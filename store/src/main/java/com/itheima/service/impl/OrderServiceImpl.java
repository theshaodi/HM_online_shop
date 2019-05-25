package com.itheima.service.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Project: com.itheima.service.impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-25 20:10
 * @Description:
 * @Version: 1.0
 */
public class OrderServiceImpl implements OrderService {

    private OrderDao OD = BeanFactory.newInstance(OrderDao.class);
    private ThreadLocal<Connection> TLC = new ThreadLocal<>();

    /**
     * 向数据库中存入某个用户的订单对象
     * @param orders 根据购物车数据生成的订单对象Orders
     * @return 写库成功返回true,否则返回false
     */
    @Override
    public boolean saveOrders(Orders orders) {
        try {
            OD.saveOrders(orders);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 按照某个用户购物车中的订单列表，将列表中的每一项存入数据库中
     * @param orderItems 用户购物车中的订单列表中每一项所构成的OrderItem列表
     * @return 全部插入成功返回true,否则返回false
     */
    @Override
    public boolean saveOrderItems(List<OrderItem> orderItems) {
        boolean isSuccess = false;
        try {
            for (OrderItem orderItem : orderItems) {
                OD.saveOrderItem(orderItem);
            }
            isSuccess = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 生成订单并将信息保存入数据库的事务版本
     * @param orders
     * @param orderItems
     * @return 失败，回滚数据库操作并返回false，成功则返回true
     */
    @Override
    public boolean saveOrdersAndOrderItems(Orders orders, List<OrderItem> orderItems) {
        Connection conn = C3P0Utils.getThreadLocalConnection(TLC);

        boolean isSuccess = false;
        try {
            conn.setAutoCommit(false);
            OD.saveOrders(conn,orders);
            for (OrderItem orderItem : orderItems) {
                OD.saveOrderItem(conn,orderItem);
            }
            conn.commit();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            try{
                conn.rollback();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        return isSuccess;
    }
}
