package com.itheima.dao.cargo;


import com.itheima.domain.cargo.Export;
import com.itheima.domain.cargo.ExportExample;

import java.util.List;

public interface IExportDao {
    /**
     * 根据id删除
     */
    int deleteByPrimaryKey(String id);


    /**
     * 保存
     */
    int insertSelective(Export record);

    /**
     * 条件查询
     */
    List<Export> selectByExample(ExportExample example);

    /**
     * 根据id查询
     */
    Export selectByPrimaryKey(String id);

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(Export record);
}