package com.itheima.dao.system;

import com.itheima.domain.system.SysLog;

import java.util.List;

public interface ISysLogDao {
    //查询全部
    List<SysLog> findAll(String companyId);

    //添加
    int save(SysLog log);

    //系统访问人员压力(根据小时来统计)
    List findOnlineData(String companyId);
}