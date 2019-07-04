package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.IFactoryDao;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.IFactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FactoryServiceImpl implements IFactoryService {

    @Autowired
    private IFactoryDao factoryDao;

    /**
     * 保存
     *
     * @param factory
     */
    @Override
    public void save(Factory factory) {
        factory.setId(UtilFuns.generateId());
        factoryDao.insertSelective(factory);
    }

    /**
     * 更新
     *
     * @param factory
     */
    @Override
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查询
     *
     * @param id
     */
    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        List<Factory> factoryList = factoryDao.selectByExample(example);
        return factoryList;
    }
}
