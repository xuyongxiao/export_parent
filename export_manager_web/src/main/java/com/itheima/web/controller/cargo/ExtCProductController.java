package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.IExtCproductService;
import com.itheima.service.cargo.IFactoryService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCProductController extends BaseController {

    @Reference
    private IExtCproductService extCproductService;

    @Reference
    private IFactoryService factoryService;

    /**
     * 附件列表
     * @param contractId
     * @param contractProductId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //需要携带的信息：contractId、contractProductId
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);

        //列表显示
        ExtCproductExample extCproductExample = new ExtCproductExample();
        extCproductExample.createCriteria().andContractIdEqualTo(contractId);
        PageInfo pageInfo = extCproductService.findAll(extCproductExample, page, size);
        request.setAttribute("page",pageInfo);

        //附件厂家下拉列表
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);
        return "cargo/extc/extc-list";
    }

    /**
     * 新建、更新的保存按钮
     * @param extCproduct
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct){
        if (UtilFuns.isEmpty(extCproduct.getId())){
            //保存
            extCproduct.setCompanyId(super.getCurrentUserCompanyId());
            extCproduct.setCompanyName(super.getCurrentUserCompanyName());
            extCproductService.save(extCproduct);
        }else{
            //更新
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?contractId="+extCproduct.getContractId()+"&contractProductId="+extCproduct.getContractProductId();
    }

    /**
     *
     * @param id
     * @param contractId
     * @param contractProductId
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId){
        //将需要携带的信息：contractId、contractProductId存入请求域
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);

        //将全部厂家列表存入请求域。携带全部附件厂家下拉列表
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //将extCproduct的内容存入请求域。携带需要更新的extCproduct的内容
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct",extCproduct);
        return "cargo/extc/extc-update";
    }

    @RequestMapping("/delete")
    public String delete(String id, String contractId, String contractProductId){
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }

}
