package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.IContractDao;
import com.itheima.dao.cargo.IContractProductDao;
import com.itheima.dao.cargo.IExtCproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.IContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ContractProductServiceImpl implements IContractProductService {

    @Autowired
    private IContractProductDao contractProductDao;

    @Autowired
    private IContractDao contractDao;

    @Autowired
    private IExtCproductDao extCproductDao;


    /**
     * 保存
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {
        double amount = 0d;
        if (contractProduct.getCnumber()!=null && contractProduct.getPrice()!=null){
            amount =contractProduct.getPrice() * contractProduct.getCnumber() ;
        }
        contractProduct.setAmount(amount);
        contractProduct.setId(UtilFuns.generateId());
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        contract.setProNum(contract.getProNum()+1);
        //增加货物
        contractProductDao.insertSelective(contractProduct);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 更新
     * @param contractProduct
     */
    @Override
    public void update(ContractProduct contractProduct) {
        //查询此款货物原来的金额
        ContractProduct dbContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        double dbAmount = dbContractProduct.getAmount();
        //计算此款货物现在的金额
        double amount = 0d;
        if (contractProduct.getCnumber()!=null && contractProduct.getPrice()!=null){
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        contractProduct.setAmount(amount);
        //查询此款货物对应的合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //更新此款货物的金额
        contract.setTotalAmount(contract.getTotalAmount()-dbAmount+amount);
        //更新货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        //1、根据id查询、删除货物
        ContractProduct product = contractProductDao.selectByPrimaryKey(id);
        double productAmount = product.getAmount();
        contractProductDao.deleteByPrimaryKey(id);
        //2、根据条件查询、删除附件
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        double extCproductAmount = 0d;
        for (ExtCproduct extCproduct : extCproductList) {
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            extCproductAmount+=extCproduct.getAmount();
        }
        //查询、扣减对应货物的合同金额、货物款数
        Contract contract = contractDao.selectByPrimaryKey(product.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-productAmount-extCproductAmount);
        contract.setProNum(contract.getProNum()-1);
        contract.setExtNum(contract.getExtNum()-extCproductList.size());
        contractDao.updateByPrimaryKeySelective(contract);

    }

    /**
     * 根据id查询
     * @param id
     */
    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * @param example
     * @param page
     * @param size
     */
    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ContractProduct> productList = contractProductDao.selectByExample(example);
        return new PageInfo(productList);
    }

    /**
     * 查询厂家销售数量和厂家清单
     * @param companyId
     * @return
     */
    @Override
    public List findFactoryData(String companyId) {
        List factoryData = contractProductDao.findFactoryData(companyId);
        return factoryData;
    }

    /**
     * 产品销量排行
     * @param companyId
     * @return
     */
    @Override
    public List findSellDate(String companyId) {
        List sellDateList = contractProductDao.findSellDate(companyId);
        return sellDateList;
    }
}
