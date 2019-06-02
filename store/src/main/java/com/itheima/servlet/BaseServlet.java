
package com.itheima.servlet;

import com.itheima.domain.User;
import com.itheima.utils.Result;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
/**
 * @author ：seanyang
 * @date ：Created in 2019/3/27 14:25
 * @description ：Servlet基类
 *  1. 解决中文获取与响应中文字符集问题
 *  2. 根据客户端method参数值，动态调用web层方法
 *     如果method有值，调用以该值命名的web方法
 *     如果method没有值，不执行任何操作
 * @version: 1.0
 */
public class BaseServlet extends HttpServlet {
    /**
     * doGet方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=utf-8");
            request.setCharacterEncoding("utf-8");

            String md = request.getParameter("method");
            System.out.println("method:"+md);
            if(md != null && md.length()>0) {
                Class clazz = this.getClass();
                Method method = clazz.getMethod(md, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(this, request, response);
            }
        }catch (Exception ex){ex.printStackTrace();}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * 公共方法，判断是否已登录
     */
    public void isLogin(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        User user = (User)request.getSession().getAttribute("user");
        if( user == null){
            // 用户未登录
            Result res = new Result(Result.NOLOGIN,"尚未登录");
            response.getWriter().print(JSONObject.fromObject(res));
            return;
        }
        // 登陆成功后，给浏览器写入cookie
        Cookie userCookie = new Cookie("user", user.getUsername());
        userCookie.setPath("/");
        userCookie.setMaxAge(60*10);
        // 设置Cookie所属的主域名
        userCookie.setDomain("madai.site");
        response.addCookie(userCookie);
        printResult(Result.SUCCESS,"已登陆",response);
    }

    /**
     * 公共方法，响应JSON数据到前端
     */
    public void  printResult(int resultTag,String message,HttpServletResponse response) throws ServletException,IOException{
        printResult(resultTag,message,null,response);
    }
    public void  printResult(int resultTag,String message,Object obj,HttpServletResponse response) throws ServletException,IOException{
        Result result = new Result(resultTag,message,obj);
        response.getWriter().print(JSONObject.fromObject(result));
    }
}
