package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.SysLog;

import java.util.List;

public interface ISysLogService {

    PageInfo findByPageHelper(String companyId, int page, int size);

    void save(SysLog log);

    /**
     * 系统访问压力
     * @param companyId
     * @return
     */
    List findOnlineData(String companyId);
}
