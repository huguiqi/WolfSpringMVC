package com.wolf.service;

import com.wolf.dao.UserDao;
import com.wolf.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "loginUserService")
public class LoginUserService {

    @Autowired
    private UserDao userDao;

    public Account getUserByUserName(String userName) {
       Account account = userDao.getUserByUserName(userName);
       return account;
    }
}
