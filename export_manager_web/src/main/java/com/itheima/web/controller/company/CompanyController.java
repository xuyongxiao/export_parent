package com.itheima.web.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Company;
import com.itheima.service.company.ICompanyService;
import com.itheima.web.controller.BaseController;
import com.itheima.web.exception.CustomerException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Reference
    private ICompanyService companyService;

    /**
     * 获取企业列表
     * @param
     * @return
     */
    @RequiresPermissions("企业管理")
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        //1、调用业务层查询
        PageInfo pageInfo = companyService.findByPageHelper(page, size);
        //2、存入请求域中
        request.setAttribute("page",pageInfo);
        //3、转发到列表页面
        return "company/company-list";
    }
    /*
    //无分页
    @RequestMapping("/list")
    public String list(HttpServletRequest request){
        //int i = 1/0;
        //1、调用业务层查询
        List<Company> companyList = companyService.findAll();
        //2、存入请求域中
        request.setAttribute("list",companyList);
        //3、转发到列表页面
        return "company/company-list";
    }
    */

    /**
     * 前往新增页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "company/company-add";
    }

    /**
     * 保存或新增
     * @param company
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Company company) throws Exception {
        if (UtilFuns.isEmpty(company.getName())){
            throw new CustomerException("企业名称不存在...");
        }

        if (UtilFuns.isEmpty(company.getId())){
            //保存
            companyService.save(company);
        }else {
            //更新
            companyService.update(company);
        }
        //重定向到更新页面
        return "redirect:/company/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //查询
        Company company = companyService.findById(id);
        //存到请求域
        request.setAttribute("company",company);
        //转发到更新页面
        return "/company/company-update";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        companyService.delete(id);
        return "redirect:/company/list.do";
    }
}
