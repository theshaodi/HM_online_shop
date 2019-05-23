package com.itheima.domain;

import com.itheima.utils.ArithMoney;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/27 13:49
 * @description ：订单项实体类
 * @version: 1.0
 */
public class OrderItem {
    // 订单项数量
    private Integer count;
    // 订单项小计，计算和返回数据时，用金钱工具类做处理
    private Double subTotal;
    // 商品ID
    private String pid;
    // 订单ID
    private String oid;

    @Override
    public String toString() {
        return "OrderItem{" +
                "count=" + count +
                ", subTotal=" + subTotal +
                ", pid='" + pid + '\'' +
                ", oid='" + oid + '\'' +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubTotal() {
        return ArithMoney.get(subTotal);
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
