package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.IExportProductDao;
import com.itheima.domain.cargo.ExportProduct;
import com.itheima.domain.cargo.ExportProductExample;
import com.itheima.service.cargo.IExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExportProductServiceImpl implements IExportProductService {

    @Autowired
    private IExportProductDao exportProductDao;


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public ExportProduct findById(String id) {
        return null;
    }

    /**
     * 保存
     *
     * @param exportProduct
     */
    @Override
    public void save(ExportProduct exportProduct) {

    }

    /**
     * 更新
     *
     * @param exportProduct
     */
    @Override
    public void update(ExportProduct exportProduct) {

    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {

    }

    /**
     * 查询全部带分页
     *
     * @param example
     * @return
     */
    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        List<ExportProduct> exportProductList = exportProductDao.selectByExample(example);
        return exportProductList;
    }
}
