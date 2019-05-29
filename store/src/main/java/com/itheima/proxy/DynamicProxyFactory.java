package com.itheima.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Project: com.itheima.proxy
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-29 23:46
 * @Description:
 * @Version: 1.0
 */
public class DynamicProxyFactory {

    /**
     * 获取动态代理对象
     * @param target  被代理对象
     * @param invocationHandler invocationHandler实现类对象
     * @return
     */
    public static Object getDynamicProxy(Object target, InvocationHandler invocationHandler){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),invocationHandler);
    }
}
