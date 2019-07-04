package com.itheima.dao.system;

import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * 部门的持久层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface IDeptDao {

    /**
     * 查询所有
     * @param companyId
     * @return
     */
    //这么写行不行？ 不行  select * from pe_dept where company_id = 当前登录用户的所属企业id
    List<Dept> findAll(String companyId);

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
}
