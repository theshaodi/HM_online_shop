package com.itheima.dao.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.OrderItemView;
import com.itheima.domain.Orders;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.io.ObjectOutputStream;
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
        QR.update(conn,sql,params);
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
        QR.update(conn,sql,params);
    }

    @Override
    public Long getOrderCountByUid(String uid) throws SQLException {
        String sql = "select count(*) from orders where uid = ?";
        Object[] params = {
                uid
        };
        return QR.query(sql,new ScalarHandler<>(),params);
    }

    @Override
    public List<Orders> getOrdersByUid(String uid, int currentPage, int pageSize) throws SQLException {
        String sql = "select * from orders where uid = ? limit ?,?";
        Object[] params = {
                uid,
                (currentPage - 1)*pageSize,
                pageSize
        };
        List<Orders> ordersList = QR.query(sql, new BeanListHandler<>(Orders.class), params);
        for (Orders orders : ordersList) {
            String sql2 = "select p.pid,p.pname,p.pimage,p.shop_price,o.count,o.subtotal from orderitem as o,product as p where o.pid = p.pid and oid = ?";
            Object[] params2 = {
                    orders.getOid()
            };
            List<OrderItemView> orderItems = QR.query(sql2, new BeanListHandler<>(OrderItemView.class), params2);
            orders.setOrderViewList(orderItems);
        }
        return ordersList;
    }

    @Override
    public Orders getOrdersByOid(String oid) throws SQLException {
        String sql = "select * from orders where oid = ?";
        Object[] params = {
                oid
        };
        Orders orders = QR.query(sql, new BeanHandler<>(Orders.class), params);
        String sql2 = "select p.pid,p.pname,p.pimage,p.shop_price,o.count,o.subtotal from orderitem as o,product as p where o.pid = p.pid and oid = ?";
        Object[] params2 = {
                orders.getOid()
        };
        List<OrderItemView> orderItems = QR.query(sql2, new BeanListHandler<>(OrderItemView.class), params2);
        orders.setOrderViewList(orderItems);
        return orders;
    }

    @Override
    public void updateOrdersNameAddrTel(String oid, String name, String addr, String tel) throws SQLException {

        String sql = "update orders set name=?, address=?, telephone=? where oid=?";
        Object[] params = {
                name,
                addr,
                tel,
                oid
        };
        QR.update(sql,params);
    }

    @Override
    public void updateOrdersStateById(String oid, int payState) throws SQLException {
        String sql = "update orders set state = ? where oid = ?";
        Object[] params = {payState,oid};
        QR.update(sql,params);
    }
}
