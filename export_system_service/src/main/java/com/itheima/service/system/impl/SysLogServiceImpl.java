package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.ISysLogDao;
import com.itheima.domain.system.SysLog;
import com.itheima.service.system.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements ISysLogService {

    @Autowired
    private ISysLogDao sysLogDao;

    @Override
    public PageInfo findByPageHelper(String companyId, int page, int size) {
        PageHelper.startPage(page,size);
        List<SysLog> sysLogList = sysLogDao.findAll(companyId);
        PageInfo pageInfo = new PageInfo(sysLogList);
        return pageInfo;
    }

    @Override
    public void save(SysLog log) {
        log.setId(UtilFuns.generateId());
        sysLogDao.save(log);
    }

    /**
     * 系统访问压力
     * @param companyId
     * @return
     */
    @Override
    public List findOnlineData(String companyId) {
        List onlineData = sysLogDao.findOnlineData(companyId);
        return onlineData;
    }
}
