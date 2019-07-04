package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.IContractDao;
import com.itheima.dao.cargo.IExtCproductDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.service.cargo.IExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExtCproductServiceImpl implements IExtCproductService {

    @Autowired
    private IExtCproductDao extCproductDao;

    @Autowired
    private IContractDao contractDao;

    /**
     * 保存
     * @param extCproduct
     */
    @Override
    public void save(ExtCproduct extCproduct) {
        //添加附件
        double amount = 0d;
        if (extCproduct.getCnumber()!=null && extCproduct.getPrice()!=null){
            amount = extCproduct.getCnumber()*extCproduct.getPrice();
        }
        extCproduct.setAmount(amount);
            //设置附件的其他信息
        extCproduct.setId(UtilFuns.generateId());
        extCproductDao.insertSelective(extCproduct);
        //更新合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        contract.setExtNum(contract.getExtNum()+1);
        contractDao.updateByPrimaryKeySelective(contract);

    }

    /**
     * 更新
     * @param extCproduct
     */
    @Override
    public void update(ExtCproduct extCproduct) {
        //对附件进行更新
        ExtCproduct dbExtCproduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        double dbAmount = dbExtCproduct.getAmount();
        double amount = 0d;
        if (extCproduct.getCnumber()!=null && extCproduct.getPrice()!=null){
            amount = extCproduct.getCnumber()*extCproduct.getPrice();
        }
        extCproduct.setAmount(amount);
        extCproductDao.updateByPrimaryKeySelective(extCproduct);

        //更新合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setTotalAmount(contract.getTotalAmount()-dbAmount+amount);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id) {
        //删除附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        double amount = extCproduct.getAmount();
        extCproductDao.deleteByPrimaryKey(id);
        //更新合同（合同amount、合同附件款数）
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setExtNum(contract.getExtNum()-1);
        contract.setTotalAmount(contract.getTotalAmount()-amount);
        contractDao.updateByPrimaryKeySelective(contract);

    }

    /**
     * 根据id查询
     * @param id
     */
    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     * @param example
     * @param page
     * @param size
     */
    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<ExtCproduct> cproductList = extCproductDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(cproductList);
        return pageInfo;
    }
}
