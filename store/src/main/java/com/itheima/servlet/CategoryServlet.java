package com.itheima.servlet;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 15:59
 * @Description: 商品分类servlet
 * @Version: 1.0
 */
@WebServlet(urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService CS = BeanFactory.newInstance(CategoryService.class);

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> categoryList = CS.findAll();
        if(categoryList != null)
            printResult(Result.SUCCESS,"商品分类查询成功",categoryList,response);
        else
            printResult(Result.FAILS,"商品分类查询失败",response);
    }
}
