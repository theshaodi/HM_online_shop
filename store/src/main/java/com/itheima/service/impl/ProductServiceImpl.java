package com.itheima.service.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.PageBean;
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

    /**
     * 获取热点商品（前九个)
     * @return type List<Product>
     */
    @Override
    public List<Product> findHostProducts() {
        try {
            return PD.findHostProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取新鲜商品(前九个)
     * @return type List<Product>
     */
    @Override
    public List<Product> findNewProducts() {
        try {
            return PD.findNewProducts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过某个商品的pid查询该商品的所有信息
     * @param pid 商品id
     * @return type Product
     */
    @Override
    public Product findProductById(String pid) {
        try {
            return PD.findProductById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页查询
     * @param cid 商品类别的cid
     * @param currentPage 前端传来的当前页码,默认为1
     * @param pageSize 每页显示的商品树数量
     * @return 返回类型PageBean<Product>
     */
    @Override
    public PageBean<Product> getProductListById(String cid, int currentPage, int pageSize) {

        PageBean<Product> ppb = new PageBean<>();

        try {
            long totalCount = PD.totalCountByCid(cid);

            List<Product> productList = PD.getProductListById(cid, currentPage, pageSize);

            int totalPage = (int) (Math.ceil(((double) totalCount) / pageSize));

            ppb.setCurrentPage(currentPage);
            ppb.setPageSize(pageSize);
            ppb.setTotalCount(totalCount);
            ppb.setList(productList);
            ppb.setTotalPage(totalPage);
            return ppb;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
