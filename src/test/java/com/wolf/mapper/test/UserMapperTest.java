package com.wolf.mapper.test;

import com.wolf.mapper.UserMapper;
import com.wolf.model.Account;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:d:/workspaces/wolfSpringMVC-mybatis/src/main/webapp/WEB-INF/conf/spring/applicationContext.xml" })
@Ignore
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void  testGetUser(){
        Account account = userMapper.getUser(1L);
       Assert.assertThat(account.getPassword(), Is.is("qiguihu"));
    }
}
