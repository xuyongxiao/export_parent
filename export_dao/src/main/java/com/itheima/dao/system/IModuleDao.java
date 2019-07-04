package com.itheima.dao.system;

import com.itheima.domain.system.Module;
import java.util.List;
import java.util.Map;

/**
 */
public interface IModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //查询全部
    List<Module> findAll();

    List<Map> findByRole(String roleId);

    List<Module> findByUserId(String userId);

    List<Module> findByBelong(Integer belong);

}