package com.itheima.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.cargo.IContractProductService;
import com.itheima.service.system.ISysLogService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Reference
    private IContractProductService contractProductService;

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping("/toCharts")
    private String toCharts(String chartsType){

        return "/stat/stat-"+chartsType;
    }

    @RequestMapping("/getFactoryData")
    public @ResponseBody List getFactoryData(){
        return contractProductService.findFactoryData(super.getCurrentUserCompanyId());
    }

    @RequestMapping("/getSellData")
    public @ResponseBody List getSellData(){
        return contractProductService.findSellDate(super.getCurrentUserCompanyId());
    }

    @RequestMapping("/getOnlineData")
    public @ResponseBody List getOnlineData(){
        return sysLogService.findOnlineData(super.getCurrentUserCompanyId());
    }

}
