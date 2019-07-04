package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.IDeptDao;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门的业务层实现类
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private IDeptDao deptDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        //1.设置分页信息
        PageHelper.startPage(page,size);
        //2.查询结果集（直接用查询所有即可）
        List<Dept> deptList = deptDao.findAll(companyId);
        //3.创建返回值并返回
        return new PageInfo(deptList);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public void save(Dept dept) {
        //1.设置id
        dept.setId(UtilFuns.generateId());
        //2.保存
        deptDao.save(dept);
    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public void delete(String id) {
        //1.根据id查询所有部门，把id当做parent_id   select * from pe_dept where parent_id = 1
        deptDao.delete(id);
    }

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }
}
