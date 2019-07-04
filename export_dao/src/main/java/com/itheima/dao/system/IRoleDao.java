package com.itheima.dao.system;

import com.itheima.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IRoleDao {

    /**
     * 查询所有
     * @return
     */
    List<Role> findAll(String companyId);

    /**
     * 根据id查询
     * @param Id
     * @return
     */
    Role findById(String Id);

    /**
     * 保存
     * @param role
     */
    void save(Role role);

    /**
     * 更新
     * @param role
     */
    void update(Role role);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    void deleteRoleModule(String roleId);

    void saveRoleModule(@Param("roleId") String roleId, @Param("moduleId") String moudleId);

    List<String> findByUserId(String id);

    void deleteRoleByUser(String id);

    void saveRoleByUser(@Param("userId") String id,@Param("RoleId") String roleId);

}
