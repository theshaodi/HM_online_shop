package com.itheima.servlet;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Project: com.itheima.servlet
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-25 15:23
 * @Description:
 * @Version: 1.0
 */
@WebServlet("/cart")
public class CartServlet extends BaseServlet{

    private ProductService PS = BeanFactory.newInstance(ProductService.class);


    /**
     * 获取session中的购物车数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if(cart == null)
            printResult(Result.FAILS,"没有获取到购物车数据,快去购物吧",response);
        else
            printResult(Result.SUCCESS,"获取到了购物车的数据",cart,response);
    }

    /**
     * 根据商品pid向购物车中添加商品
     * @param request
     * @param response
     */
    public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pid = request.getParameter("pid");
        String count = request.getParameter("count");

        int cont = 0;
        try{
            cont = Integer.parseInt(count);
        }catch (Exception ex){
            ex.printStackTrace();
        }


        // 根据pid查询产品相关信息

        Product product = PS.findProductById(pid);

        if(product == null){
            printResult(Result.FAILS,"购物车商品添加失败--没有查询到商品信息",response);
            return;
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCount(cont);
        HttpSession ssion = request.getSession();
        Cart cart = (Cart)ssion.getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            ssion.setAttribute("cart",cart);
        }
        cart.addCart(cartItem);
        System.out.println(cart);

        printResult(Result.SUCCESS,"购物车商品添加成功",response);

    }
}
