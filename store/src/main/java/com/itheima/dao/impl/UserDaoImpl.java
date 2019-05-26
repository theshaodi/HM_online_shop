package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private QueryRunner QR = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public void register(User u) throws SQLException {
        String sql = "insert into shop_user (`uid`,`username`,`password`,`name`,`email`,`birthday`,`gender`) values(?,?,?,?,?,?,?)";
        Object[] params = {u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getBirthday(),u.getGender()};
        QR.update(sql,params);
    }

    @Override
    public User login(String username, String password) throws SQLException {
        String sql = "select * from shop_user where username = ? and password = ?";
        Object[] params = {username,password};
        return QR.query(sql, new BeanHandler<>(User.class), params);
    }
}
