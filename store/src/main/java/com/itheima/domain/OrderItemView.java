package com.itheima.domain;

import com.itheima.utils.ArithMoney;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/28 10:45
 * @description ：订单显示
 * @version: 1.0
 */
public class OrderItemView {
    // 商品主键
    private String pid;
    // 商品名称
    private String pname;
    // 商品图片
    private String pimage;
    // 商品价格
    private double shop_price;
    // 购买的数量
    private int count;
    // 订单展示项小计，计算和返回数据时，用金钱工具类做处理
    private double subTotal;

    @Override
    public String toString() {
        return "OrderItemView{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", pimage='" + pimage + '\'' +
                ", shop_price=" + shop_price +
                ", count=" + count +
                ", subTotal=" + subTotal +
                '}';
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubTotal() {
        return ArithMoney.get(subTotal);
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
