package com.itheima.service;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;

import java.util.List;

/**
 * @Project: com.itheima.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-25 19:40
 * @Description:
 * @Version: 1.0
 */
public interface OrderService {
    boolean saveOrders(Orders orders);
    boolean saveOrderItems(List<OrderItem> orderItems);
    boolean saveOrdersAndOrderItems(Orders orders,List<OrderItem> orderItems);
}
