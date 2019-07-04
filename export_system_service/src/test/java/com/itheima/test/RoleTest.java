package com.itheima.test;

import com.itheima.domain.system.Role;
import com.itheima.service.system.IRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class RoleTest {

    @Autowired
    private IRoleService roleService;

    @Test
    public void findAllTest(){
        List<Role> roleList = roleService.findAll("1");
        for (Role role : roleList) {
            System.out.println(role);
        }
    }

    @Test
    public void save(){
        Role role = new Role();
        role.setName("张三");
        role.setOrderNo(111L);
        role.setRemark("些什么");
        roleService.save(role);
    }


}
