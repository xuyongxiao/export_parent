package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;
import java.util.Map;

public interface IRoleService {

    List<Role> findAll(String companyId);

    PageInfo findByPageHelper(String companyId, int page, int size);

    Role findById(String id);

    void save(Role role);

    void update(Role role);

    void delete(String id);

    /**
     * 根据RoleId查询对应的模块信息
     * @param roleId
     * @return
     */
    List<Map> findModuleByRole(String roleId);

    /**
     * 根据roleId更新roleId的模块信息。
     * @param id
     * @param modules
     */
    void updateRoleModule(String id, String[] modules);
}
