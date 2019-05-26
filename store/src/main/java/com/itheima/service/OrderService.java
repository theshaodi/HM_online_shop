package com.itheima.service;

import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;

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

    // 订单列表查询
    PageBean<Orders> getPageBeanByUid(String uid,int currentPage,int pageSize);

    // 订单详情查询
}
