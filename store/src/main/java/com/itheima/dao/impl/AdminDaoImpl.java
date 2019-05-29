package com.itheima.dao.impl;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: com.itheima.dao.impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-28 20:40
 * @Description:
 * @Version: 1.0
 */
public class AdminDaoImpl implements AdminDao {

    private QueryRunner QR = new QueryRunner(C3P0Utils.getDataSource());

    @Override
    public List<Category> findAllCategory() throws SQLException {
        String sql = "select * from category";
        return QR.query(sql,new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(String cid, String cname) throws SQLException {
        String sql = "insert into category (cid, cname) values(?,?)";
        Object[] params = {
                cid,
                cname
        };
        QR.update(sql,params);
    }

    @Override
    public void deleteCategoryById(String cid) throws SQLException {
        String sql = "delete from category where cid = ?";
        Object[] params = {
                cid
        };
        QR.update(sql,params);
    }

    @Override
    public Category getCategoryByCid(String cid) throws SQLException {
        String sql = "select * from category where cid = ?";
        Object[] params = {
                cid
        };
        return QR.query(sql,new BeanHandler<>(Category.class),params);
    }

    @Override
    public void updateCategoryByCid(Category c) throws SQLException {
        String sql = "update category set cname = ? where cid = ?";
        Object[] params = {
                c.getCname(),
                c.getCid()
        };
        QR.update(sql,params);
    }

    @Override
    public User loginAdmin(String uname, String password) throws SQLException {
        String sql = "select * from admin_user where username = ? and password = ?";
        Object[] params = {
                uname,
                password
        };
        return QR.query(sql,new BeanHandler<>(User.class),params);
    }

    /**
     * 分页查询所有商品并按时间倒序排列
     * @param currentPage 当前页码
     * @param pageSize 每页展示的商品个数
     * @return 返回每页包含的商品列表
     */
    @Override
    public List<Product> findProductsByPage(int currentPage, int pageSize) throws SQLException {
        String sql = "select * from product order by pdate desc limit ?,?";
        Object[] params = {
                currentPage,
                pageSize
        };
        return QR.query(sql,new BeanListHandler<>(Product.class),params);
    }

    @Override
    public Long getProductCount() throws SQLException {
        String sql = "select count(pid) from product";
        return QR.query(sql,new ScalarHandler<Long>());
    }

    /**
     * 根据封装的商品对象，将其加入数据库中
     * @param product 由前端页面传来的商品信息所封装的商品对象
     * @throws SQLException
     */
    @Override
    public void addProduct(Product product) throws SQLException {
        String sql = "insert into product (pid,pname,market_price,shop_price,pimage,pdate,is_hot,pdesc,pflag,cid)" +
                " values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {
                product.getPid(),
                product.getPname(),
                product.getMarket_price(),
                product.getShop_price(),
                product.getPimage(),
                product.getPdate(),
                product.getIs_hot(),
                product.getPdesc(),
                product.getPflag(),
                product.getCid()
        };
        QR.update(sql,params);
    }
}
