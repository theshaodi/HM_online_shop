package com.itheima.domain;

import com.itheima.utils.ArithMoney;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/27 08:49
 * @description ：购物车项
 * @version: 1.0
 */
public class CartItem {
    // 购买商品
    private Product product;
    // 购买数量
    private  int count;
    // 购物小计，计算和返回数据时，用金钱工具类做处理
    private  double subTotal;

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", count=" + count +
                ", subTotal=" + subTotal +
                '}';
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 购物项小计
     * 价格：商品单价*购物数据
     * @return
     */
    public double getSubTotal() {
        // 利用工具类进行金额计算
        subTotal = ArithMoney.mul(product.getShop_price(),count);
        return ArithMoney.get(subTotal);
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
