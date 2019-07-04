package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * 部门的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface IDeptService {

    /**
     * 查询所有 带分页
     * @param companyId
     * @return
     */
    PageInfo findAll(String companyId, int page, int size);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Dept findById(String id);

    /**
     * 保存
     * @param dept
     */
    void save(Dept dept);

    /**
     * 更新
     * @param dept
     */
    void update(Dept dept);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询所有部门，不带分页
     * @param companyId
     * @return
     */
    List<Dept> findAll(String companyId);
}
