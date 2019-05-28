package com.itheima.dao.impl;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Category;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

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
}
