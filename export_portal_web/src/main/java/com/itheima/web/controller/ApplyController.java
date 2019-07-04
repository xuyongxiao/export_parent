package com.itheima.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.system.Company;
import com.itheima.service.company.ICompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {

    @Reference
    private ICompanyService companyService;

    @RequestMapping("/apply")
    public @ResponseBody String apply(Company company){
        companyService.save(company);
        return "1";
    }

}
