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


    /**
     * 查询热点商品的方法
     * @return
     * @throws SQLException
     */
    List<Product> findHostProducts() throws SQLException;


    /**
     * 查询新鲜商品的方法
     * @return
     * @throws SQLException
     */
    List<Product> findNewProducts() throws SQLException;


    /**
     * 根据产品id查询商品详细信息
     * @param pid 商品id
     * @return
     * @throws SQLException
     */
    Product findProductById(String pid) throws SQLException;


    /**
     * 分页查询
     * @param cid 商品类别的cid
     * @param currentPage 前端传来的当前页码,默认为1
     * @param pageSize 每页显示的商品树数量
     * @return 返回类型List<Product>
     * @throws SQLException
     */
    List<Product> getProductListById(String cid,int currentPage,int pageSize) throws SQLException;


    /**
     * 通过商品分类的cid获取该商品分类下的商品总数
     * @param cid 商品分类id
     * @return type int
     * @throws SQLException
     */
    long totalCountByCid(String cid) throws SQLException;
}
