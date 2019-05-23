package com.itheima.filter;
/**
 * @author ：seanyang
 * @date ：Created in 2019/3/27 14:25
 * @description ：过滤器
 *   实现ajax跨域访问
 * @version: 1.0
 */
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = "/*")
public class AjaxFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //实现ajax跨域访问,设置响应头
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        // 获取当前访问Origin(http://域名:端口号)，比如http://www.itheimashop.com, http://127.0.0.1:8020
        String originHeader=((HttpServletRequest) servletRequest).getHeader("Origin");
        // 设置这个请求头，可以跨域访问，实际开发中会有域名白名单
        response.setHeader("Access-Control-Allow-Origin",originHeader);
        // 支持Cookie的跨域Ajax请求
        response.setHeader("Access-Control-Allow-Credentials","true");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
