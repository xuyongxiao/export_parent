package com.itheima.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;

import java.util.List;

public interface IContractService {

    //查询全部
    List<Contract> findAll(ContractExample example);

	//根据id查询
    Contract findById(String id);

    //保存
    void save(Contract contract);

    //更新
    void update(Contract contract);

    //删除
    void delete(String id);

    //分页查询
	public PageInfo findByPageHelper(ContractExample example, int page, int size);
}
