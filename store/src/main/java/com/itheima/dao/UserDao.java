package com.itheima.dao;

import com.itheima.domain.User;

import java.sql.SQLException;

public interface UserDao {
    void register(User u) throws SQLException;
    User login(String username,String password) throws SQLException;
}
