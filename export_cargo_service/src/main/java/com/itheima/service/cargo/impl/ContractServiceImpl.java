package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.IContractDao;
import com.itheima.dao.cargo.IContractProductDao;
import com.itheima.dao.cargo.IExtCproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.IContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class ContractServiceImpl implements IContractService {

    @Autowired
    private IContractDao contractDao;

    @Autowired
    private IContractProductDao contractProductDao;

    @Autowired
    private IExtCproductDao extCproductDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Contract findById(String id) {
        Contract contract = contractDao.selectByPrimaryKey(id);
        return contract;
    }

    /**
     * 保存
     * @param contract
     */
    @Override
    public void save(Contract contract) {
        //设置id
        contract.setId(UtilFuns.generateId());
        //设置其他信息
        contract.setState(0);   //状态设置为草稿
        contract.setProNum(0);   //新合同货物数量0
        contract.setExtNum(0);    //新合同附件数量为0
        contract.setTotalAmount(0d);    //新合同附件金额为0
        contract.setCreateTime(new Date());    //创建时间
        contractDao.insertSelective(contract);
    }

    /**
     * 更新
     * @param contract
     */
    @Override
    public void update(Contract contract) {
        contract.setUpdateTime(new Date());
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        Contract contract = contractDao.selectByPrimaryKey(id);
        //删附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        for (ExtCproduct extCproduct : extCproductList) {
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //删货物
        ContractProductExample contractProductExample = new ContractProductExample();
        contractProductExample.createCriteria().andContractIdEqualTo(id);
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        for (ContractProduct contractProduct : contractProductList) {
            contractProductDao.deleteByPrimaryKey(contractProduct.getId());
        }
        //删合同
        contractDao.deleteByPrimaryKey(id);
    }

    /**
     * 查全部带分页
     * @param example
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByPageHelper(ContractExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Contract> contractList = contractDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(contractList);
        return pageInfo;
    }

    /**
     * 查全部
     * @param example
     * @return
     */
    @Override
    public List<Contract> findAll(ContractExample example) {
        List<Contract> contractList = contractDao.selectByExample(example);
        return contractList;
    }

}
