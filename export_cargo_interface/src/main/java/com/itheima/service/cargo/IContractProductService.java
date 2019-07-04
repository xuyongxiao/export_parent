package com.itheima.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ContractProduct;
import com.itheima.domain.cargo.ContractProductExample;

import java.util.List;

/**
 * 业务层接口
 * ...
 */
public interface IContractProductService {

	/**
	 * 保存
	 */
	void save(ContractProduct contractProduct);
//TODO khghjgjh
	/**
	 * 更新
	 */
	void update(ContractProduct contractProduct);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	ContractProduct findById(String id);

	/**
	 * 分页查询
	 */
	PageInfo findAll(ContractProductExample example, int page, int size);

	/**
	 * 查询厂家销售数量和厂家清单
	 * @param companyId
	 * @return
	 */
    List findFactoryData(String companyId);

	/**
	 * 产品销量排行
	 * @param companyId
	 * @return
	 */
	List findSellDate(String companyId);

}
