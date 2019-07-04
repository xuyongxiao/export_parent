package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Module;
import com.itheima.service.system.IModuleService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private IModuleService moduleService;

    @RequiresPermissions("模块管理")
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        PageInfo pageInfo = moduleService.findByPageHelper(page, size);
        request.setAttribute("page",pageInfo);
        return "/system/module/module-list";
    }

    @RequestMapping("/edit")
    public String edit(Module module){
        if (UtilFuns.isEmpty(module.getId())){
            moduleService.save(module);
        }else{
            Module dbmodule = moduleService.findById(module.getId());
            BeanUtils.copyProperties(module,dbmodule);
            moduleService.update(dbmodule);
        }
        return "redirect:/system/module/list.do";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus",moduleList);
        return "/system/module/module-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Module module = moduleService.findById(id);
        request.setAttribute("module",module);
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus",moduleList);
        return "/system/module/module-update";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }

}
