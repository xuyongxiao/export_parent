package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.cargo.ContractProduct;
import com.itheima.domain.cargo.ContractProductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.IContractProductService;
import com.itheima.service.cargo.IFactoryService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private IContractProductService contractProductService;

    @Reference
    private IFactoryService factoryService;

    /**
     * 前往货物、附件页面
     * @param contractId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //生产厂家下拉选择框选项
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);

        //分页查询全部
        ContractProductExample productExample = new ContractProductExample();
            //设置查询条件，合同的id等于形参的contractId。
            // 其实就是设置条件：ss_contract_product表中的contractId项值为方法中传过来的参数contractId的值
        productExample.createCriteria().andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(productExample,page,size);
        request.setAttribute("page",pageInfo);

        //携带生产厂家信息
        request.setAttribute("contractId",contractId);
        return "cargo/product/product-list";
    }

    /**
     * 前往上传货物页面
     * @param contractId
     * @return
     */
    @RequestMapping("/toImport")
    public String toImport(String contractId){
        return "cargo/product/product-import";
    }

    /**
     * 新建、更新的保存按钮
     * @param contractProduct
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ContractProduct contractProduct){
        if (UtilFuns.isEmpty(contractProduct.getId())){
            //保存
            contractProduct.setCompanyId(super.getCurrentUserCompanyId());
            contractProduct.setCompanyName(super.getCurrentUserCompanyName());
            contractProductService.save(contractProduct);
        }else{
            //修改
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();
    }

    /**
     * 货物列表页面前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct",contractProduct);
        //生产厂家下拉选择框选项
        FactoryExample factoryExample = new FactoryExample();
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList",factoryList);
        return "cargo/product/product-update";
    }

    /**
     * 删除货物
     * @param id
     * @param contractId
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id, String contractId){
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }


}
