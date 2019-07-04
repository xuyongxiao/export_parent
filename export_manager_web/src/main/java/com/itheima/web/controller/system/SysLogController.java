package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.service.system.ISysLogService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

    @Autowired
    private ISysLogService sysLogService;

    @RequiresPermissions("日志管理")
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size ){
        PageInfo pageInfo = sysLogService.findByPageHelper(super.getCurrentUserCompanyId(),page,size);
        request.setAttribute("page",pageInfo);
        return "/system/log/log-list";
    }
}
