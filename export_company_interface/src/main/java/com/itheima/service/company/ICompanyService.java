package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.system.Company;

import java.util.List;

/**
 * 企业的业务层接口
 */
public interface ICompanyService {

    /**
     * 查询所有
     * @return List<Company>
     */
    List<Company> findAll();

    /**
     * 根据id查询
     * @param id
     * @return Company
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

    /**
     * 查询带有分页的结果集
     * @param page
     * @param size
     * @return
     */
    PageInfo findByPageHelper(int page, int size);


}
