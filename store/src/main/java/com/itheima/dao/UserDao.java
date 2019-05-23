package com.itheima.dao;

import com.itheima.domain.User;

import java.sql.SQLException;

/**
* @Author: 少迪
* @Date: Created in 2019-05-23 22:00
* @Description: 有关于用户相关操作的dao层接口，包括但不限于注册，登陆操作
* @Version: 1.0
*/

public interface UserDao {

    /**
     * 根据User对象注册用户数据
     * @param u type User
     * @throws SQLException
     */
    void register(User u) throws SQLException;


    /**
     * 根据前端传来的username及对应的password，查询相关User
     * @param username 用户名
     * @param password 密码
     * @return User 对象
     * @throws SQLException
     */
    User login(String username,String password) throws SQLException;
}
