package com.itheima.dao;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Project: com.itheima.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-25 20:12
 * @Description:
 * @Version: 1.0
 */
public interface OrderDao {
    void saveOrders(Orders orders) throws SQLException;
    void saveOrderItem(OrderItem orderItem) throws SQLException;
    void saveOrders(Connection conn, Orders orders) throws SQLException;
    void saveOrderItem(Connection conn, OrderItem orderItem) throws SQLException;

    // 查询订单列表
    Long getOrderCountByUid(String uid) throws SQLException;
    List<Orders> getOrdersByUid(String uid, int currentPage, int pageSize) throws SQLException;

    // 查询订单详情
    Orders getOrdersByOid(String oid) throws SQLException;

    // 确认订单后更新补充数据库中订单的收货人信息
    void updateOrdersNameAddrTel(String oid, String name, String addr, String tel) throws SQLException;

    void updateOrdersStateById(String oid,int payState) throws SQLException;
}
