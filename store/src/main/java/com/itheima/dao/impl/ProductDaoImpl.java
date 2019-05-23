package com.itheima.dao.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 16:35
 * @Description: 产品Dao层实现类
 * @Version: 1.0
 */
public class ProductDaoImpl implements ProductDao {

    private QueryRunner QR = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public List<Product> findHostProducts() throws SQLException {
        String sql = "select * from product where is_hot=1 limit 0,9";
        return QR.query(sql,new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findNewProducts() throws SQLException {
        String sql = "select * from product order by pdate desc limit 0,9;";
        return QR.query(sql,new BeanListHandler<>(Product.class));
    }

    @Override
    public Product findProductById(String pid) throws SQLException {
        String sql = "select * from product where pid = ?";
        Object[] params = {
                pid
        };
        return QR.query(sql,new BeanHandler<>(Product.class),params);
    }
}
