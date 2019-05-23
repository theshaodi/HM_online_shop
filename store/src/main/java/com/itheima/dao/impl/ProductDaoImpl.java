package com.itheima.dao.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Product;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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

    /**
     * 分页查询
     * @param cid 商品类别的cid
     * @param currentPage 前端传来的当前页码,默认为1
     * @param pageSize 每页显示的商品树数量
     * @return 返回类型List<Product>
     * @throws SQLException
     */
    @Override
    public List<Product> getProductListById(String cid, int currentPage, int pageSize) throws SQLException {
        String sql = "select * from product where cid = ? limit ?,?";
        Object[] params = {
                cid,
                (currentPage-1)*pageSize,
                pageSize
        };
        return QR.query(sql,new BeanListHandler<Product>(Product.class),params);
    }

    /**
     * 通过商品分类的cid获取该商品分类下的商品总数
     * @param cid 商品分类id
     * @return type int
     * @throws SQLException
     */
    @Override
    public long totalCountByCid(String cid) throws SQLException {
        String sql = "select count(pid) from product where cid = ?";
        Object[] params = {
                cid
        };
        return QR.query(sql, new ScalarHandler<Long>(),params);
    }
}
