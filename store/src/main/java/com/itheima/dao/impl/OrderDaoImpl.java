package com.itheima.dao.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Project: com.itheima.dao.impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-25 20:13
 * @Description:
 * @Version: 1.0
 */
public class OrderDaoImpl implements OrderDao {

    private QueryRunner QR = new QueryRunner(C3P0Utils.getDataSource());
    private QueryRunner SQR = new QueryRunner();

    @Override
    public void saveOrders(Orders orders) throws SQLException {
        String sql = "insert into orders (oid,uid,ordertime,total,state) values(?,?,?,?,?)";
        Object[] params = {
                orders.getOid(),
                orders.getUid(),
                orders.getOrdertime(),
                orders.getTotal(),
                orders.getState()
        };
        QR.update(sql,params);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) throws SQLException {
        String sql = "insert into `orderitem` (oid,pid,subtotal,count) values(?,?,?,?)";
        Object[] params = {
                orderItem.getOid(),
                orderItem.getPid(),
                orderItem.getSubTotal(),
                orderItem.getCount()
        };
        QR.update(sql,params);
    }

    @Override
    public void saveOrders(Connection conn, Orders orders) throws SQLException {

        String sql = "insert into orders (oid,uid,ordertime,total,state) values(?,?,?,?,?)";
        Object[] params = {
                orders.getOid(),
                orders.getUid(),
                orders.getOrdertime(),
                orders.getTotal(),
                orders.getState()
        };
        SQR.update(conn,sql,params);
    }

    @Override
    public void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException {
        String sql = "insert into `orderitem` (oid,pid,subtotal,count) values(?,?,?,?)";
        Object[] params = {
                orderItem.getOid(),
                orderItem.getPid(),
                orderItem.getSubTotal(),
                orderItem.getCount()
        };
        SQR.update(conn,sql,params);
    }
}
