package com.itheima.web.controller;


import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.IModuleService;
import com.itheima.service.system.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController{

    @Autowired
    private IUserService userService;

    @Autowired
    private IModuleService moduleService;

	@RequestMapping("/login")
	public String login(String email,String password) {
	    //判断是否有email，如果没有直接到登录页面
	/*    if (UtilFuns.isEmpty(email)){
            return "redirect:/login.jsp";
        }

	 */
        /**
         * 使用传统方式登录
        User user = userService.findByEmail(email);
		if (user == null){
            request.setAttribute("error","邮箱不存在！");
            return "forward:/login.jsp";
        }
        if (!user.getPassword().equals(password)){
            request.setAttribute("error","密码错误！");
            return "forward:/login.jsp";
        }
        **/

	    //获取shiro对象
        Subject currentUser = SecurityUtils.getSubject();
        //登录
            //获取用户名密码令牌
        UsernamePasswordToken token = new UsernamePasswordToken(email,password);
        try {
            //使用令牌登录
            currentUser.login(token);
        }catch (Exception ae){
            request.setAttribute("error","使用shiro安全登录，用户名或密码错误！");
            return "forward:/login.jsp";
        }

        //获取用户Principal（主体）及User对象
        User user = (User) currentUser.getPrincipal();
        //动态菜单获取
        session.setAttribute("user",user);
        List<Module> moduleList = moduleService.findModleByUser(user);
        session.setAttribute("modules",moduleList);
        //跳转到主界面
        return "home/main";
	}

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        //SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
