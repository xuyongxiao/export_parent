package com.itheima.service.cargo;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;

import java.util.List;

public interface IExportProductService {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	ExportProduct findById(String id);

	/**
	 * 保存
	 * @param exportProduct
	 */
	void save(ExportProduct exportProduct);

	/**
	 * 更新
	 * @param exportProduct
	 */
	void update(ExportProduct exportProduct);

	/**
	 * 删除
	 * @param id
	 */
	void delete(String id);

	/**
	 * 查询全部不带分页
	 * @param example
	 * @return
	 */
	List<ExportProduct> findAll(ExportProductExample example);
}
