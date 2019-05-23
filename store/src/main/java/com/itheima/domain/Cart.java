package com.itheima.domain;

import com.itheima.utils.ArithMoney;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/27 08:52
 * @description ：购物车对象
 *  包含金额，购物项
 *  购物项可能是多个商品组成
 *  集合Map<主键,值>
 * @version: 1.0
 */
public class Cart {
    // 购物总金额，计算和返回数据时，用金钱工具类做处理
    private double total;
    // 购物项集合
    private Map<String,CartItem> cartItemMap = new HashMap<>();
    @Override
    public String toString() {
        return "Cart{" +
                "total=" + total +
                ", cartItemMap=" + cartItemMap +
                '}';
    }
    public double getTotal() {
        // 使用金钱工具类，返回精确到分的数据
        return ArithMoney.get(total);
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setCartItemMap(Map<String, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }
    /**
     * 返回购车项数据集合
     * @return
     */
    public Collection<CartItem> getCartItemMap() {
        return cartItemMap.values();
    }
    /**
     * 添加购物车方法
     * 购物项CartItem，存储到购物车Map集合
     *   判断:
     *     购物车中是否有这个购物项目
     *     有，取出，修改数量
     *     无，直接添加购物项
     *   计算总价格
     * @param cartItem （商品，数量、小计）
     */
    public void addCart(CartItem cartItem){
        String pid = cartItem.getProduct().getPid();
        //  购物车中是否有这个购物项目
        if(cartItemMap.containsKey(pid)){
            // 有，取出，修改数量
            CartItem oldItem = cartItemMap.get(pid);
            oldItem.setCount(oldItem.getCount()+cartItem.getCount());
        }else{
            // 无，直接添加购物项
            cartItemMap.put(pid,cartItem);
        }
        // 使用金钱工具类，计算总价格
        total = ArithMoney.add(total,cartItem.getSubTotal());
    }

    /**
     * 根据PID，移除购物项，重新计算总价格
     * @param pid
     */
    public void removeCart(String pid){
        CartItem item = cartItemMap.remove(pid);
        total -= item.getSubTotal();
    }
    /**
     * 清空购物项，总价为0
     */
    public void clearCart(){
        cartItemMap.clear();
        total = 0.0;
    }
}
