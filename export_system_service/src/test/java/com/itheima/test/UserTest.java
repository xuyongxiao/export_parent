package com.itheima.test;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.User;
import com.itheima.domain.system.UserExample;
import com.itheima.service.system.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class UserTest {

    @Autowired
    private IUserService userService;

    @Test
    public void findAllTest(){
        List<User> userList = userService.findAll(new UserExample());
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void findByPageHelperTest(){
        PageInfo pageInfo = userService.findByPageHelper(new UserExample(), 1, 5);
        for (Object object : pageInfo.getList()) {
            System.out.println(object);
        }
    }

    @Test
    public void saveTest(){
        User user = new User();
        user.setUserName("测试user");
        userService.save(user);
    }
}
