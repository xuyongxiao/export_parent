package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Role;
import com.itheima.service.system.IRoleService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("角色管理")
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        PageInfo pageInfo = roleService.findByPageHelper(super.getCurrentUserCompanyId(), page, size);
        request.setAttribute("page",pageInfo);
        return "/system/role/role-list";
    }

    @RequestMapping("/edit")
    public String edit(Role role){
        if (UtilFuns.isEmpty(role.getId())){
            role.setCompanyId(super.getCurrentUserCompanyId());
            role.setCompanyName(super.getCurrentUserCompanyName());
            roleService.save(role);
        }else{
            Role dbRole = roleService.findById(role.getId());
            role.setCompanyId(dbRole.getCompanyId());
            role.setCompanyName(dbRole.getCompanyName());
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "/system/role/role-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Role role = roleService.findById(id);
        request.setAttribute("role",role);
        return "/system/role/role-update";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    @RequestMapping("/roleModule")
    public String roleModule(@RequestParam("roleId") String id){
        Role role = roleService.findById(id);
        request.setAttribute("role",role);
        return "/system/role/role-module";
    }

    @RequestMapping("/initModuleData")
    public @ResponseBody List<Map> initModuleData(String id){ //id来自页面，有的通过get方式，有的通过Post方式获取
        List<Map> moduleByRole = roleService.findModuleByRole(id);
        return moduleByRole;
    }

    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(@RequestParam("roleid") String id, String[] moduleIds){
        for (String moduleId : moduleIds) {
            System.out.println(moduleId);
        }
        roleService.updateRoleModule(id,moduleIds);
        return "redirect:/system/role/list.do";
    }
}
