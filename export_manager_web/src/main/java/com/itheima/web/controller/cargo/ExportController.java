package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.IExportProductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.IContractService;
import com.itheima.service.cargo.IExportProductService;
import com.itheima.service.cargo.IExportService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private IExportService exportService;
    @Reference
    private IExportProductService exportProductService;
    @Reference
    private IContractService contractService;

/*-----------------------------------合同管理模块-------------------------------------------------------------*/

    /**
     * 购销合同列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        ContractExample example = new ContractExample();
        example.setOrderByClause("create_time DESC");
        PageInfo pageInfo = contractService.findByPageHelper(example, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/export/export-contractList";
    }

    /**
     * 前往查看页面
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id){
        Export export = exportService.findById(id);
        request.setAttribute("export",export);
        return "cargo/export/export-view";
    }

    /**
     * 前往报运页面
     * @param contract
     * @return
     */
    @RequestMapping("/toExport")
    public String toExport(Contract contract){
        request.setAttribute("id",contract.getId());
        return "cargo/export/export-toExport";
    }

/*-----------------------------------出口报运模块-------------------------------------------------------------*/

    /**
     * 报运单列表
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){
        ExportExample example = new ExportExample();
        example.createCriteria().andCompanyIdEqualTo(super.getCurrentUserCompanyId());
        example.setOrderByClause("create_time DESC");
        PageInfo pageInfo = exportService.findAll(example, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/export/export-list";
    }

    /**
     * 保存和更新页面的保存按钮
     * @param export
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Export export){
        if (UtilFuns.isEmpty(export.getId())){
            //保存
            export.setCompanyId(super.getCurrentUserCompanyId());
            export.setCompanyName(super.getCurrentUserCompanyName());
            exportService.save(export);
        }else{
            //更新
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //携带报运单信息
        Export export = exportService.findById(id);
        request.setAttribute("export",export);
        //携带报运单货物信息
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id).andCompanyIdEqualTo(super.getCurrentUserCompanyId());
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        request.setAttribute("eps",exportProductList);
        return "cargo/export/export-update";
    }



    /**
     * 报运单删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        exportService.delete(id);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        Export export = exportService.findById(id);
        if (export.getState() != 1){
            export.setState(1);
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        Export export = exportService.findById(id);
        if (export.getState() != 0){
            export.setState(0);
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

}
