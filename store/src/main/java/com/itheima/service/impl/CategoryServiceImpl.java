package com.itheima.service.impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 15:37
 * @Description: 商品分类service接口实现类
 * @Version: 1.0
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao CD = BeanFactory.newInstance(CategoryDao.class);

    @Override
    public List<Category> findAll() {
        try {
            return CD.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
