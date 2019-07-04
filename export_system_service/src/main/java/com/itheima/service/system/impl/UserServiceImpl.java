package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.Encrypt;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.IRoleDao;
import com.itheima.dao.system.IUserDao;
import com.itheima.domain.system.User;
import com.itheima.domain.system.UserExample;
import com.itheima.service.system.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<User> findAll(UserExample example) {
        return userDao.selectByExample(example);
    }

    @Override
    public PageInfo findByPageHelper(UserExample example, int page, int size) {
        //设置PageHelper参数
        PageHelper.startPage(page,size);
        List<User> userList = userDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public User findById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(User user) {
        //全球唯一标识符
        user.setId(UtilFuns.generateId());
     //   user.setJoinDate(new Date());
        String password = Encrypt.md5(user.getPassword(), user.getEmail());
        user.setPassword(password);
        userDao.insertSelective(user);
    }

    @Override
    public void update(User user) {
        String password = Encrypt.md5(user.getPassword(), user.getEmail());
        user.setPassword(password);
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(String id) {
        userDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<String> findRoleByUserId(String id) {
        List<String> roleList = roleDao.findByUserId(id);
        return roleList;
    }

    @Override
    public void changeRole(String id, String[] roleIds) {
        roleDao.deleteRoleByUser(id);
        for (String roleId : roleIds) {
            roleDao.saveRoleByUser(id,roleId);
        }
    }

    @Override
    public User findByEmail(String email) {
        User user = userDao.findByEmail(email);
        return user;
    }


}
