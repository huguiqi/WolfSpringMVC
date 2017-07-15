package com.wolf.dao;


import com.wolf.mapper.UserMapper;
import com.wolf.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDao {

    @Autowired
    private UserMapper userMapper;
    
    public Account getUserByUserName(String userName){
        return userMapper.getUserByUserName(userName);
    }

}
