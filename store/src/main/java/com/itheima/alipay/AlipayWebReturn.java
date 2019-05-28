package com.itheima.alipay;


import com.itheima.common.OrderConst;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.BeanFactory;
import com.sun.tools.corba.se.idl.constExpr.Or;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：seanyang
 * @date ：Created in 2019/4/21$
 * @description ：支付宝支付同步返回
 * @version: 1.0
 */
@WebServlet("/alipay/web/return")
public class AlipayWebReturn extends HttpServlet {

    private OrderService OS = BeanFactory.newInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(AlipayWebPay.signVerified(request.getParameterMap())) {
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            System.out.println(out_trade_no);
            //订单状态,修改为已经付款,正式上线，改为在AlipayWebNotify中完成
            if(OS.updateOrdersStateById(out_trade_no, OrderConst.PAID)){
                System.out.println("状态更新成功！");
            }else{
                System.out.println("状态更新失败！");
            }
            System.out.println("AlipayWebReturn 成功");
            request.setAttribute("oid",out_trade_no);
            request.getRequestDispatcher("/success.jsp").forward(request,response);
        }else {
            response.getWriter().println("验签失败");
            System.out.println("AlipayWebReturn 失败");
        }
    }
}
