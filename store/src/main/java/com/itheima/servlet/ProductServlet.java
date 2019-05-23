package com.itheima.servlet;

import com.itheima.common.ProductConst;
import com.itheima.domain.PageBean;
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

    /**
     * 通过商品类别的cid分页查找该类商品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getProductListById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        if(currentPage == null || "".equals(currentPage) || "null".equalsIgnoreCase(currentPage))
            currentPage = "1";

        PageBean<Product> pbByCid = PS.getProductListById(cid, Integer.parseInt(currentPage), ProductConst.PAGE_SIZE);
        if(pbByCid != null)
            printResult(Result.SUCCESS,"查到了该商品分类下相关的商品信息",pbByCid,response);
        else
            printResult(Result.FAILS,"没有查询到该商品分类相关的商品信息",response);

    }

    /**
     * 通过商品pid查询该商品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");

        Product product = PS.findProductById(pid);

        if(product != null)
            printResult(Result.SUCCESS,"获取到商品详情信息",product,response);
        else
            printResult(Result.FAILS,"未获取到商品信息",response);
    }

    /**
     * 查找热点商品和新鲜商品的前九个(为首页查询)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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
