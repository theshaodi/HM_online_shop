package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDao UD = BeanFactory.newInstance(UserDao.class);

    @Override
    public boolean register(User u) {

        try {
            UD.register(u);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        User u = null;
        try {
            u = UD.login(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
}
