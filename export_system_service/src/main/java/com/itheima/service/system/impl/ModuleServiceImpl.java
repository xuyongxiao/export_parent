package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.IModuleDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private IModuleDao moduleDao;

    @Override
    public PageInfo findByPageHelper(int page, int size) {
        PageHelper.startPage(page,size);
        List<Module> moduleList = moduleDao.findAll();
        PageInfo pageInfo = new PageInfo(moduleList);
        return pageInfo;
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public void save(Module module) {
        module.setId(UtilFuns.generateId());
        moduleDao.save(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public List<Module> findModleByUser(User user) {
        if (user.getDegree() == 0){
            return moduleDao.findByBelong(0);
        }else if(user.getDegree() == 1){
            return moduleDao.findByBelong(1);
        }else {
            List<Module> moduleList = moduleDao.findByUserId(user.getId());
            return moduleList;
        }
    }
}
