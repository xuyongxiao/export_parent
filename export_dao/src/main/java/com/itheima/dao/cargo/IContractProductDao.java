package com.itheima.dao.cargo;

import com.itheima.domain.cargo.ContractProduct;
import com.itheima.domain.cargo.ContractProductExample;

import java.util.List;

public interface IContractProductDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(ContractProduct record);

	//条件查询
    List<ContractProduct> selectByExample(ContractProductExample example);

	//id查询
    ContractProduct selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(ContractProduct record);

    //查询厂家销售数量和厂家清单
    List findFactoryData(String companyId);

    //产品销量排行
    List findSellDate(String companyId);
}