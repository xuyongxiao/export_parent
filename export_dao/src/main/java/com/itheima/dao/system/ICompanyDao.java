package com.itheima.dao.system;

import com.itheima.domain.system.Company;

import java.util.List;

/**
 * 企业的持久层接口
 */
public interface ICompanyDao {

    /**
     * 查询所有
     * @return
     */
    List<Company> findAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 保存
     * @param company
     */
    void save(Company company);

    /**
     * 更新
     * @param company
     */
    void update(Company company);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);
}
