package com.itheima.service.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;
import com.sun.tools.corba.se.idl.constExpr.Or;
import sun.jvm.hotspot.debugger.Page;

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

    public boolean updateOrdersStateById(String oid,int payState){
        try {
            OD.updateOrdersStateById(oid,payState);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

        Connection conn = C3P0Utils.getThreadLocalConnection();

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

        if(conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isSuccess;
    }

    /**
     * 通过用户id，分页查询并返回该用户订单列表的pageBean
     * @param uid 用户id
     * @param currentPage 前端传来的当前页码，默认为1
     * @param pageSize 每页展示的订单数
     * @return type PageBean<Orders>
     */
    @Override
    public PageBean<Orders> getPageBeanByUid(String uid, int currentPage, int pageSize) {
        PageBean<Orders> opb = new PageBean<>();

        try {
            Long orderCountByUid = OD.getOrderCountByUid(uid);
            List<Orders> ordersByUid = OD.getOrdersByUid(uid, currentPage, pageSize);
            int totalPage = (int)Math.ceil(((double)orderCountByUid)/pageSize);
            opb.setCurrentPage(currentPage);
            opb.setPageSize(pageSize);
            opb.setList(ordersByUid);
            opb.setTotalCount(orderCountByUid);
            opb.setTotalPage(totalPage);
            return opb;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Orders getOrdersByOid(String oid) {
        try {
            return OD.getOrdersByOid(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateOrdersNameAddrTel(String oid, String name, String addr, String tel) {
        try {
            OD.updateOrdersNameAddrTel(oid,name,addr,tel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
