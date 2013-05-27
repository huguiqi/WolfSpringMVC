package com.wolf.mapper;

import com.wolf.model.Account;


public interface UserMapper {

    Account getUser(Long userId);

    Account getUserByUserName(String userName);
}