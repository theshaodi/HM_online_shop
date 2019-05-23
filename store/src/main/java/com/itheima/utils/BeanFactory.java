package com.itheima.utils;


import java.util.ResourceBundle;

/**
 *  Bean工厂对象
 *  方法:
 *    传递接口,传递的是接口的class文件对象
 *    返回实现类的对象
 *
 *    泛型: 本身没有类型,未知的数据类型
 *    调用的时候,传递什么,就是什么类型
 */
public class BeanFactory {
    public static <T>T newInstance(Class<T> clazz){
        //获取传递的接口名
        String name = clazz.getSimpleName();
        //读取配置文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bean");
        String className = resourceBundle.getString(name);
        System.out.println(className);
        //反射创建对象
        try {
            return (T)Class.forName(className).newInstance();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
