package com.itheima.servlet;

import com.itheima.common.ProductConst;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.exception.DeleteCategoryException;
import com.itheima.service.AdminService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @Project: com.itheima.servlet
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-28 20:46
 * @Description:
 * @Version: 1.0
 */
@WebServlet(urlPatterns = "/admin")
public class AdminServlet extends BaseServlet{

    private AdminService AS = BeanFactory.newInstance(AdminService.class);

    /**
     * admin后端分页查询商品数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getProductsByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String currentPage = request.getParameter("currentPage");
        if(currentPage == null || "".equals(currentPage) || "null".equals(currentPage))
            currentPage = "1";
        PageBean<Product> ppb = AS.findProductsByPage(Integer.parseInt(currentPage), ProductConst.PAGE_SIZE);
        if(ppb != null)
            printResult(Result.SUCCESS,"分页查询商品信息成功",ppb,response);
        else
            printResult(Result.FAILS,"分页查询商品信息失败",response);
    }

    /**
     * admin账户登出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        printResult(Result.SUCCESS,"admin成功登出",response);
    }

    public void isLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User adminuser = (User) request.getSession().getAttribute("adminuser");
        if( adminuser == null){
            // 用户未登录
            Result res = new Result(Result.NOLOGIN,"尚未登录");
            response.getWriter().print(JSONObject.fromObject(res));
            return;
        }
        User user = AS.loginAdmin(adminuser.getUsername(), adminuser.getPassword());
        if(user != null)
            printResult(Result.SUCCESS,"已登陆admin",user,response);
        else
            printResult(Result.FAILS,"该账户不属于admin,请使用admin账户",response);
    }

    /**
     * 用于后台系统登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loginAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = AS.loginAdmin(username, password);
        if(user != null){
            request.getSession().setAttribute("adminuser",user);
            printResult(Result.SUCCESS,"admin登陆成功",user,response);
        }else{
            printResult(Result.FAILS,"登陆失败",response);
        }
    }

    /**
     * 根据cid更新某个商品分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> categoryFormMap = request.getParameterMap();
        System.out.println(categoryFormMap);
        Category category = new Category();
        try {
            BeanUtils.populate(category,categoryFormMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(category);
        boolean b = AS.updateCategoryByCid(category);
        if(b)
            printResult(Result.SUCCESS,"成功按cid更新该商品分类",response);
        else
            printResult(Result.FAILS,"更新商品分类失败",response);
    }

    /**
     * 按商品分类cid查询该商品分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");

        Category category = AS.getCategoryByCid(cid);
        if(category != null)
            printResult(Result.SUCCESS,"成功按cid查询到该商品分类",category,response);
        else
            printResult(Result.FAILS,"查询商品分类失败",response);
    }

    /**
     * 获取所有的商品分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> allCategory = AS.findAllCategory();
        if(allCategory != null)
            printResult(Result.SUCCESS,"查询所有的商品分类成功",allCategory,response);
        else
            printResult(Result.FAILS,"查询所有的商品分类失败",response);
    }

    /**
     * 添加商品分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("cname");

        boolean b = AS.addCategory(cname);
        if(b)
            printResult(Result.SUCCESS,"添加商品分类成功",response);
        else
            printResult(Result.FAILS,"添加商品分类失败",response);
    }

    /**
     * 按照商品分类cid删除某个商品分类，因外键约束导致的异常，应给用户友好提示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteCategoryByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");

        boolean b = false;
        try {
            b = AS.deleteCategoryByCid(cid);
        } catch (DeleteCategoryException e) {
            printResult(Result.FAILS,e.getMessage(),response);
            return;
        }
        if(b)
            printResult(Result.SUCCESS,"删除商品分类成功",response);
        else
            printResult(Result.FAILS,"删除商品分类失败",response);
    }
}
