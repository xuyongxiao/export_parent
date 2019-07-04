package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.ICompanyDao;
import com.itheima.domain.system.Company;
import com.itheima.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements ICompanyService {

    @Autowired
    private ICompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void save(Company company) {
        //1、生成id
        /*
         * UUID：全球唯一标识符
         * .replace 替换“-”
         * toUpperCase 全部大写
         */
        String id = UUID.randomUUID().toString().replace("-","").toUpperCase();
        //2、给company赋值id
        company.setId(id);
        //3、调用companyDao保存
        companyDao.save(company);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    @Override
    public PageInfo findByPageHelper(int page, int size) {
        //获取第page页，size条内容，默认查询总数count
        PageHelper.startPage(page, size);
        //查询所有
        List<Company> companyList = companyDao.findAll();
        //创建返回值对象
        PageInfo pageInfo = new PageInfo(companyList);
        //返回
        return pageInfo;
    }
}
