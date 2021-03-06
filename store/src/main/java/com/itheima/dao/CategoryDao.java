package com.itheima.dao;

import com.itheima.domain.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 15:28
 * @Description: 商品分类Dao接口
 * @Version: 1.0
 */
public interface CategoryDao {

    /**
     * 查询所有的商品分类
     * @return type List(Category)
     * @throws SQLException
     */
    List<Category> findAll() throws SQLException;
}
