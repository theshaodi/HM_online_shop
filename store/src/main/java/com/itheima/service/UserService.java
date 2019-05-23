package com.itheima.service;

import com.itheima.domain.User;

/**
* @Author: 少迪
* @Date: Created in 2019-05-23 21:59
* @Description: 有关于用户相关的操作service的接口，包括但不限于注册，登陆操作
* @Version: 1.0
*/

public interface UserService {

    /**
     * 根据User对象注册用户数据
     * @param u type User
     * @return 注册成功返回true 否则返回false
     */
    boolean register(User u);

    /**
     * 根据前端传来的username及对应的password，查询相关User
     * @param username 用户名
     * @param password 密码
     * @return User 对象
     */
    User login(String username,String password);
}
