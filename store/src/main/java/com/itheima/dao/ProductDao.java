package com.itheima.dao;

import com.itheima.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 16:32
 * @Description: 产品Dao层接口
 * @Version: 1.0
 */
public interface ProductDao {
    List<Product> findHostProducts() throws SQLException;
    List<Product> findNewProducts() throws SQLException;
}
