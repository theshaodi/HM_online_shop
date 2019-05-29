package com.itheima.servlet;

import com.itheima.alipay.AlipayWebPay;
import com.itheima.common.OrderConst;
import com.itheima.domain.*;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;
import com.itheima.utils.UUIDUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Project: com.itheima.servlet
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-25 19:22
 * @Description: 用来处理订单方面请求的类
 * @Version: 1.0
 */
@WebServlet(urlPatterns = "/order")
public class OrderServlet extends BaseServlet{

    private OrderService OS = BeanFactory.newInstance(OrderService.class);

    /**
     * 确认收货人信息，并将订单号和订单金额传递给支付宝，将支付宝返回给我们的页面
     * 传递给用户，用户调用支付宝支付接口，完成支付
     * 支付宝调用return_url更新订单信息
     * 最后跳转回订单详情页
     * 支付宝沙箱账号:
     * 我的:kvrbye3573@sandbox.com
     * 老师的:cmvxpf7015@sandbox.com
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String oid = request.getParameter("oid");

        OS.updateOrdersNameAddrTel(oid,name,address,telephone);
        String webPayBody = null;
        try {
            webPayBody = AlipayWebPay.getWebPayBody(oid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(webPayBody != null){
            printResult(Result.SUCCESS,"更新订单收货人信息，成功生成支付页面信息",webPayBody,response);
        }else{
            printResult(Result.FAILS,"生成支付页面失败",response);
        }
    }

    /**
     * 通过订单id查询订单信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getOrdersByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 判断用户是否登陆
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            printResult(Result.NOLOGIN, "没登陆是不能下订单的哟", response);
            return;
        }

        String oid = request.getParameter("oid");
        Orders orders = OS.getOrdersByOid(oid);
        if(orders.getState() == OrderConst.UN_PAID){
            AlipayWebPay.queryTrade(orders.getOid(),"");
            orders = OS.getOrdersByOid(oid);
        }

        printResult(Result.SUCCESS,"订单详情查询成功",orders,response);
    }

    /**
     * 通过用户id获取该用户的所有订单列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getOrdersListByUid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 判断用户是否登陆
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            printResult(Result.NOLOGIN, "没登陆是不能下订单的哟", response);
            return;
        }

        String currentPage = request.getParameter("currentPage");
        if(currentPage == null || "".equals(currentPage) || "null".equals(currentPage))
            currentPage = "1";

        PageBean<Orders> pbByUid = OS.getPageBeanByUid(user.getUid(), Integer.parseInt(currentPage), OrderConst.PAGE_SIZE);

        if(pbByUid != null ){
            printResult(Result.SUCCESS,"订单分页查询成功",pbByUid,response);
        }else{
            printResult(Result.FAILS,"订单分页查询失败",response);
        }
    }

    /**
     * 验证用户是否登陆
     * 购物车中是否有采购记录
     * 生成订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 判断用户是否登陆
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            printResult(Result.NOLOGIN, "没登陆是不能下订单的哟", response);
            return;
        }

        // 查看购物车中是否有采购记录
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(cart == null || cart.getCartItemMap().size() == 0){
            printResult(Result.FAILS, "没有采购记录,不能下订单哟",response);
            return;
        }

        // 生成订单
        Orders orders = new Orders();
        orders.setOid(UUIDUtils.getUUID());
        orders.setUid(user.getUid());
        orders.setState(OrderConst.UN_PAID);
        orders.setTotal(cart.getTotal());
        // 生成订单的时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTime = simpleDateFormat.format(new Date());
        orders.setOrdertime(orderTime);

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItemMap()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(orders.getOid());
            orderItem.setPid(cartItem.getProduct().getPid());
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubTotal(cartItem.getSubTotal());
            orderItems.add(orderItem);
        }

        System.out.println("orders===>"+orders);
        System.out.println("orderItems====>"+orderItems);

        // 将订单保存到数据库
        /**
         * 不带回滚的版本
         */
//        if(OS.saveOrders(orders) && OS.saveOrderItems(orderItems)){
            // 成功生成订单后，清空购物车,返回成功的响应信息
//            cart.clearCart();
//            printResult(Result.SUCCESS, "订单生成成功并完整保存进了数据库", response);
//        }else{
//            printResult(Result.FAILS,"没有将生成的订单数据完整存入数据库",response);
//        }

        if(OS.saveOrdersAndOrderItems(orders,orderItems)){
        // 成功生成订单后，清空购物车,返回成功的响应信息
            cart.clearCart();
            printResult(Result.SUCCESS, "订单生成成功并完整保存进了数据库", response);
        }else{
            printResult(Result.FAILS,"没有将生成的订单数据完整存入数据库",response);
        }


    }
}
