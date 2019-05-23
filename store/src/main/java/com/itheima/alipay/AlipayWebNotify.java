package com.itheima.alipay;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：seanyang
 * @date ：Created in 2019/4/21$
 * @description ：支付宝支付异步通知
 * @version: 1.0
 */
@WebServlet("/alipay/web/notify")
public class AlipayWebNotify extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        if(AlipayWebPay.signVerified(request.getParameterMap())) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            // 发送给支付宝
            response.getWriter().println("success");
            System.out.println("notify_success");

            //订单状态,修改为已经付款
            OrderService orderService = new OrderServiceImpl();
            if(orderService.updateOrdersStateById(out_trade_no, OrderConst.PAID)){
                System.out.println("状态更新成功！");
            }else{
                System.out.println("状态更新失败！");
            }
        }else {//验证失败
            response.getWriter().println("fail");
            System.out.println("notify_fail");
        }
        */
    }
}
