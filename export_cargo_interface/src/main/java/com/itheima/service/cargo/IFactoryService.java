package com.itheima.service.cargo;

import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;

import java.util.List;

/**
 */
public interface IFactoryService {

	/**
	 * 保存
	 */
	void save(Factory factory);

	/**
	 * 更新
	 */
	void update(Factory factory);

	/**
	 * 删除
	 */
	void delete(String id);

	/**
	 * 根据id查询
	 */
	Factory findById(String id);

	//查询所有
	public List<Factory> findAll(FactoryExample example);
}
