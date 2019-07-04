package com.itheima.test;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.SysLog;
import com.itheima.service.system.ISysLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class LogTest {

    @Autowired
    private ISysLogService sysLogService;

    @Test
    public void findAll(){
        PageInfo pageInfo = sysLogService.findByPageHelper("1", 1, 10);
        for (Object log : pageInfo.getList()) {
            SysLog log1 = (SysLog) log;
            System.out.println(log1.getTime());
        }
    }

}
