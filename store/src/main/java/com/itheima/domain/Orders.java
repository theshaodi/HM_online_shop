package com.itheima.domain;

import com.itheima.utils.ArithMoney;

import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/27 13:48
 * @description ：订单实体类
 * @version: 1.0
 */
public class Orders {
    // 订单id
    private String oid;
    // 订单时间
    private String ordertime;
    // 订单总金额，计算和返回数据时，用金钱工具类做处理
    private Double total;
    // 订单状态
    private Integer state;// 订单状态 0:未付款 1:已付款 2:已发货 3.已完成
    // 收获地址
    private String address;
    // 收货人姓名
    private String name;
    // 收获人电话
    private String telephone;
    // 用户ID
    private String uid;

    // 订单包含多个订单项
    private List<OrderItemView> orderViewList;

    public List<OrderItemView> getOrderViewList() {
        return orderViewList;
    }

    public void setOrderViewList(List<OrderItemView> orderViewList) {
        this.orderViewList = orderViewList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "oid='" + oid + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", total=" + total +
                ", state=" + state +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return ArithMoney.get(total);
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
