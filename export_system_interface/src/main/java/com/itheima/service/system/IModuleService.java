package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;

import java.util.List;

public interface IModuleService {

    PageInfo findByPageHelper(int page, int size);

    Module findById(String id);

    List<Module> findAll();

    void save(Module module);

    void update(Module module);

    void delete(String id);

    List<Module> findModleByUser(User user);
}
