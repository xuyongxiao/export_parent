package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.system.User;
import com.itheima.service.cargo.IContractService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private IContractService contractService;

    /**
     * 合去往同列表页
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int size){
        //创建查询条件对象
        ContractExample example = new ContractExample();
        //添加排序条件
        example.setOrderByClause("create_time desc");
        //创建Criteria对象，用于
        ContractExample.Criteria criteria = example.createCriteria();
        //获取当前用户
        User user = super.getCurrentUser();
        //获取当前用户等级参数
        Integer degree = user.getDegree();
        //判断用户等级
        switch (degree){
            case 0:
                //saas管理员，应该什么都看不到
                throw new IllegalStateException("当前用户为saas管理员，不能查看企业数据");
            case 1:
                //企业管理员，可以看当前企业全部数据
                criteria.andCompanyIdEqualTo(user.getCompanyId());
                break;
            case 2:
                //部门总监，可以看当前部门及其子部门数据
                break;
            case 3:
                //部门经理，可以查看本部门数据
                criteria.andCreateDeptEqualTo(user.getDeptId());
                break;
            case 4:
                //普通员工，只能查看自己的数据
                criteria.andCreateByEqualTo(user.getId());
                break;
            default:
                break;
        }
        //设置查询条件，企业id等于当前登录用户企业id
        example.createCriteria().andCompanyIdEqualTo(super.getCurrentUserCompanyId());
        PageInfo pageInfo = contractService.findByPageHelper(example, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/contract/contract-list";
    }

    /**
     * 新建、更新页面的保存按钮
     * @param contract
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Contract contract){
        if (UtilFuns.isEmpty(contract.getId())){
            //保存
            contract.setCompanyId(super.getCurrentUserCompanyId());
            contract.setCompanyName(super.getCurrentUserCompanyName());
            contract.setCreateBy(super.getCurrentUser().getId());
            contract.setCreateDept(super.getCurrentUser().getDeptId());
            contractService.save(contract);
        }else{
            //更新
            contract.setUpdateBy(super.getCurrentUser().getId());
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 前往新建页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "cargo/contract/contract-add";
    }

    /**
     * 前往查看页面
     * @param id
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String id){
        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "cargo/contract/contract-view";
    }

    /**
     * 删除按钮
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Contract contract = contractService.findById(id);
        request.setAttribute("contract",contract);
        return "cargo/contract/contract-update";
    }

    /**
     * 提交按钮，改变合同状态为1
     * @param id
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id){
        Contract contract = contractService.findById(id);
        contract.setState(1);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 取消提交按钮，改变合同状态为0
     * @param id
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id){
        Contract contract = contractService.findById(id);
        contract.setState(0);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    /**
     * 异步验证是否允许提交和取消
     * @param id
     * @return
     */
    @RequestMapping("/checkUpdateState")
    public @ResponseBody String checkUpdateState(String id){
        Contract contract = contractService.findById(id);
        if(contract.getState() > 1){//2  3  4  5   6
            return "0";//不允许在提交和取消的返回值
        }
        return "1";//可以提交或者取消
    }

    @RequestMapping("/print")
    public String printExcel(){
        return "cargo/print/contract-print";
    }

}
