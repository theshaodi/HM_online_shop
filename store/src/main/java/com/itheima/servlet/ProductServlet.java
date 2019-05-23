package com.itheima.servlet;

import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Project: com.itheima.servlet
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 17:08
 * @Description: 产品Servlet
 * @Version: 1.0
 */
@WebServlet(urlPatterns = "/product")
public class ProductServlet extends BaseServlet{

    private ProductService PS = BeanFactory.newInstance(ProductService.class);

    public void findProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");

        Product product = PS.findProductById(pid);

        if(product != null)
            printResult(Result.SUCCESS,"获取到商品详情信息",product,response);
        else
            printResult(Result.FAILS,"未获取到商品信息",response);
    }

    public void findHostAndNewProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Product> hostProducts = PS.findHostProducts();
        List<Product> newProducts = PS.findNewProducts();

        if(hostProducts != null && newProducts != null){
            HashMap<String, List<Product>> HWProducts = new HashMap<>();
            HWProducts.put("hostProducts",hostProducts);
            HWProducts.put("newProducts",newProducts);
            printResult(Result.SUCCESS,"成功获取热点商品和新鲜商品",HWProducts,response);
        }else{
            printResult(Result.FAILS,"获取热点商品和新鲜商品失败",response);
        }
    }
}
