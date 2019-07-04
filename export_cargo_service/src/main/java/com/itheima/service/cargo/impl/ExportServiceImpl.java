package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.*;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.IExportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ExportServiceImpl implements IExportService {

    @Autowired
    private IExportDao exportDao;
    @Autowired
    private IExportProductDao exportProductDao;
    @Autowired
    private IExtCproductDao extCproductDao;
    @Autowired
    private IContractDao contractDao;
    @Autowired
    private IContractProductDao contractProductDao;
    @Autowired
    private IExtEproductDao extEproductDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    /**
     * 保存
     * @param export
     */
    @Override
    public void save(Export export) {
        //给报运单设置id
        export.setId(UtilFuns.generateId());
        //设置新报运单的状态：草稿
        export.setState(0);
        //设置报运单创建时间
        export.setCreateTime(new Date());

        //2、修改合同状态
        //customerContract是合同或确认书号，由所有的合同组成
        String customerContract = "";
        //获取报运单实体中购销合同id集合
        String[] contractIds = export.getContractIds().split(",");
        for (String contractId : contractIds) {
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            //修改状态
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
            customerContract += contract.getContractNo() + " ";
        }
        export.setCustomerContract(customerContract);

        //3、将contract下的product封装到export下的product
        // 用于存储货物id。 key：合同下货物id；value：报运单下的货物id
        HashMap<String, String> map = new HashMap<>();
        ContractProductExample contractProductExample = new ContractProductExample();
        contractProductExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        //遍历合同货物到报运货物下
        for (ContractProduct contractProduct : contractProductList) {
            ExportProduct exportProduct = new ExportProduct();
            BeanUtils.copyProperties(contractProduct,exportProduct);
            exportProduct.setId(UtilFuns.generateId());
            exportProduct.setExportId(export.getId());
            exportProductDao.insertSelective(exportProduct);
            map.put(contractProduct.getId(),exportProduct.getId());
        }

        //把合同下附件封装到报运附件下
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdIn(Arrays.asList(contractIds));
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        for (ExtCproduct extCproduct : extCproductList) {
            ExtEproduct extEproduct = new ExtEproduct();
            BeanUtils.copyProperties(extCproduct,extEproduct);
            //合同下货物的附件是可以获取到货物id的
            extEproduct.setExportProductId(map.get(extCproduct.getContractProductId()));
            extEproduct.setExportId(export.getId());
            extEproductDao.insertSelective(extEproduct);
        }
        export.setExtNum(extCproductList.size());
        export.setProNum(contractProductList.size());
        exportDao.insertSelective(export);
    }

    /**
     * 更新
     * @param export
     */
    @Override
    public void update(Export export) {
        exportDao.updateByPrimaryKeySelective(export);
        List<ExportProduct> exportProductList = export.getExportProducts();
        if (exportProductList != null){
            for (ExportProduct exportProduct : exportProductList) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        exportDao.deleteByPrimaryKey(id);
    }

    /**
     * 查询全部带分页
     * @param example
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Export> exportList = exportDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(exportList);
        return pageInfo;
    }
}
