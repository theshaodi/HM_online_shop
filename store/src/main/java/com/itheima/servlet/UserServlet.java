package com.itheima.servlet;

import com.google.gson.Gson;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;
import com.itheima.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
* @Author: ShaoDi Wang
* @Date: Created in 2019-05-23 21:55
* @Description: 有关于用户相关操作的类，包括但不限于注册，登陆，登出操作
* @Version: 1.0
*/

@WebServlet(urlPatterns = "/user")
public class UserServlet extends BaseServlet {

    private UserService US = BeanFactory.newInstance(UserService.class);

    /**
     * 用户登出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setDomain("itheimashop.com");
        response.addCookie(cookie);

        printResult(Result.SUCCESS,"成功登出",response);
    }

    /**
     * 用户登录
     * @param request req对象
     * @param response res对象
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User u = US.login(username, password);

        if(u!=null){
            // 登陆成功后，向session中记录该user的信息
            request.getSession().setAttribute("user",u);
            // 登陆成功后，给浏览器写入cookie
            Cookie userCookie = new Cookie("user", username);
            userCookie.setPath("/");
            userCookie.setMaxAge(60*10);
            // 设置Cookie所属的主域名
            userCookie.setDomain("itheimashop.com");
            response.addCookie(userCookie);

            printResult(1,"登陆成功,跳转到首页",response);
        }else{
            printResult(0,"登陆失败",response);
        }
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> pm = request.getParameterMap();

        User user = new User();
        try {
            BeanUtils.populate(user,pm);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setUid(UUIDUtils.getUUID());
        System.out.println(user);

        boolean isRegister = US.register(user);
        if(isRegister){
            printResult(1,"注册成功，跳转到登陆页面",response);
        }else{
            printResult(0,"注册失败",response);
        }

    }
}
