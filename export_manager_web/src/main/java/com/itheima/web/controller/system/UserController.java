package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.MailUtil;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.*;
import com.itheima.service.system.IDeptService;
import com.itheima.service.system.IRoleService;
import com.itheima.service.system.IUserService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IRoleService roleService;

    @RequiresPermissions("用户管理")
    @RequestMapping("/list")
    public String findByHelper(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        UserExample example = new UserExample();
        example.createCriteria().andCompanyIdEqualTo(super.getCurrentUserCompanyId());
        PageInfo pageInfo = userService.findByPageHelper(example, page, size);
        request.setAttribute("page",pageInfo);
        return "/system/user/user-list";
    }

    @RequestMapping("/edit")
    public String edit(User user){
        if (UtilFuns.isEmpty(user.getId())){
            //保存
            user.setCompanyId(super.getCurrentUserCompanyId());
            user.setCompanyName(super.getCurrentUserCompanyName());
            userService.save(user);
            String content = "亲爱的"+user.getUserName()+"，您的Saas账户已经开通，初始密码为"+user.getPassword()+"欢迎加入";
            try {
                MailUtil.sendMsg("499431318@qq.com","注册成功",content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //更新 此处的user已经是页面上所填写的数据了，这里是保存按钮，这个类中传入的user是前端页面发给保存按钮的
            User dbUser = userService.findById(user.getId());
            BeanUtils.copyProperties(user,dbUser,new String[]{"companyId","companyName"});
            userService.update(dbUser);
        }
        return "redirect:/system/user/list.do"; //重定向
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        request.setAttribute("deptList",deptList);
        return "/system/user/user-add";
    }

    @RequestMapping("/toUpdate")
    public String update(String id){
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        request.setAttribute("deptList",deptList);
        User user = userService.findById(id);
        request.setAttribute("user",user);
        return "/system/user/user-update";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    @RequestMapping("/roleList")
    public String userRole(String id){
        List<Role> roleList = roleService.findAll(super.getCurrentUserCompanyId());
        request.setAttribute("roleList",roleList);
        User user = userService.findById(id);
        request.setAttribute("user",user);
        List<String> roleByUserId = userService.findRoleByUserId(id);
        request.setAttribute("userRoleStr",roleByUserId);
        return "/system/user/user-role";
    }

    @RequestMapping("/changeRole")
    public String changeRole(String userid,String[] roleIds){
        userService.changeRole(userid,roleIds);
        return "redirect:/system/user/list.do";
    }
}
