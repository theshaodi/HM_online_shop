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
    Orders getOrdersByOid(String Oid);

    // 确认订单后更新补充数据库中订单的收货人信息
    void updateOrdersNameAddrTel(String oid,String name,String addr,String tel);

    public boolean updateOrdersStateById(String oid,int payState);
}
