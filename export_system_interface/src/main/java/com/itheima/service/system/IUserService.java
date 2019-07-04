package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.User;
import com.itheima.domain.system.UserExample;

import java.util.List;

public interface IUserService {

    List<User> findAll(UserExample example);

    PageInfo findByPageHelper(UserExample example, int page, int size);

    User findById(String id);

    void save(User user);

    void update(User user);

    void delete(String id);

    List<String> findRoleByUserId(String id);

    void changeRole(String id, String[] roleIds);

    User findByEmail(String email);
}
