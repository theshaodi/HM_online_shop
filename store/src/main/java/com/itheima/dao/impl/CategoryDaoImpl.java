package com.itheima.dao.impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 15:29
 * @Description: 商品分类Dao层实现类
 * @Version: 1.0
 */
public class CategoryDaoImpl implements CategoryDao {

    private QueryRunner QR = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public List<Category> findAll() throws SQLException {
        String sql = "select * from category";
        return QR.query(sql, new BeanListHandler<>(Category.class));
    }
}
