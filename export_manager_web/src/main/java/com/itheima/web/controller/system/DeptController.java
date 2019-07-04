package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.User;
import com.itheima.service.system.IDeptService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
//继承BaseController用以解决 每一个需要将数据存入请求域的方法都需要在形参列表中增加HttpServletRequest request参数 的问题
public class DeptController extends BaseController {

    @Autowired
    private IDeptService deptService;

    /**
     * 列表页面
     * @param page
     * @param size
     * @return
     */
    @RequiresPermissions("部门管理")
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "10")int size){
        PageInfo pageInfo = deptService.findAll(super.getCurrentUserCompanyId(),page,size);
        request.setAttribute("page",pageInfo);
        return "system/dept/dept-list"; //转发
    }

    /**
     * 前往新增页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        request.setAttribute("deptList",deptList);
        return "system/dept/dept-add"; //转发
    }

    /**
     * 保存及更新
     * @param dept
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Dept dept){
        //先判断是保存还是更新
        if (UtilFuns.isEmpty(dept.getId())){
            //保存
            dept.setCompanyId(super.getCurrentUserCompanyId());
            dept.setCompanyName(super.getCurrentUserCompanyName());
            if (UtilFuns.isEmpty(dept.getParent().getId())){
                dept.setParent(null);
            }
            deptService.save(dept);
        }else{
            //更新
            Dept dbdept = deptService.findById(dept.getId());
            BeanUtils.copyProperties(dept,dbdept,new String[]{"companyId","companyName"});
            deptService.update(dbdept);
        }
        return "redirect:/system/dept/list.do"; //重定向！！！
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Dept dept = deptService.findById(id);
        request.setAttribute("dept",dept);
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        request.setAttribute("deptList",deptList);
        return "/system/dept/dept-update";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        deptService.delete(id);
        return "redirect:/system/dept/list.do";
    }
}
