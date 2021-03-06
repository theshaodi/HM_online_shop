package com.itheima.service;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;

import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 16:57
 * @Description:
 * @Version: 1.0
 */
public interface ProductService {
    List<Product> findHostProducts();
    List<Product> findNewProducts();
    Product findProductById(String pid);
    PageBean<Product> getProductListById(String cid, int currentPage, int pageSize);
}
