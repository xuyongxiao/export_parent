package com.itheima.service.cargo;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.Export;
import com.itheima.domain.cargo.ExportExample;

public interface IExportService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Export findById(String id);

    /**
     * 保存
     * @param export
     */
    void save(Export export);

    /**
     * 更新
     * @param export
     */
    void update(Export export);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询全部带分页
     * @param example
     * @param page
     * @param size
     * @return
     */
	PageInfo findAll(ExportExample example, int page, int size);
}
