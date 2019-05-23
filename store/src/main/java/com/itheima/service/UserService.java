package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
    boolean register(User u);
    User login(String username,String password);
}
