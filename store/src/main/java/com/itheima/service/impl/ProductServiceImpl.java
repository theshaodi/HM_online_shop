package com.itheima.service.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 17:00
 * @Description:
 * @Version: 1.0
 */
public class ProductServiceImpl implements ProductService {

    private ProductDao PD = BeanFactory.newInstance(ProductDao.class);

    @Override
    public List<Product> findHostProducts() {
        try {
            return PD.findHostProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> findNewProducts() {
        try {
            return PD.findNewProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
