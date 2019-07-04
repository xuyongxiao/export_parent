package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.IModuleDao;
import com.itheima.dao.system.IRoleDao;
import com.itheima.domain.system.Role;
import com.itheima.service.system.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IModuleDao moduleDao;

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public PageInfo findByPageHelper(String companyId, int page, int size) {

        PageHelper.startPage(page,size);

        List<Role> roleList = roleDao.findAll(companyId);

        PageInfo pageInfo = new PageInfo(roleList);

        return pageInfo;
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {

        String id = UUID.randomUUID().toString().replace("-","").toUpperCase();

        role.setId(id);

        roleDao.save(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public List<Map> findModuleByRole(String roleId) {
        List<Map> moduleByRole = moduleDao.findByRole(roleId);
        return moduleByRole;
    }

    @Override
    public void updateRoleModule(String id, String[] modules) {
        roleDao.deleteRoleModule(id);
        for (String module : modules) {
            roleDao.saveRoleModule(id,module);
        }
    }

}
