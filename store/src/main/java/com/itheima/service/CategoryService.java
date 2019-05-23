package com.itheima.service;

import com.itheima.domain.Category;

import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 15:36
 * @Description: 商品分类service层接口
 * @Version: 1.0
 */
public interface CategoryService {
    List<Category> findAll();
}
