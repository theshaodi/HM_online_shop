package com.itheima.utils;

/**
 * @author ：seanyang
 * @date ：Created in 2019/4/26
 * @description ：
 *  浮点数采用的是二进制的标识方式，不能准确的表达十进制的运算，
 *  两个doule、float进行运算的时候，会出现精确度的偏差
 *  在java中，涉及到金钱的计算建议采用java.math.BigDecimal
 *  当前自定义工具类，包含加减乘除的操作
 * @version: 1.0
 */
import java.math.BigDecimal;
import java.math.RoundingMode;

public  class ArithMoney {


    /**
     * 默认保留2位小数，且四舍五入
     * @param value
     * @return
     */
    public static double get(double value){
        return new BigDecimal(Double.valueOf(value)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1,double value2){
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1,double value2,int scale) throws IllegalAccessException{
        //如果精确范围小于0，抛出异常信息
        if(scale<0){
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }
}