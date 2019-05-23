package com.itheima.utils;

/**
 *  响应的结果对象
 *  封装3个成员
 *    状态码 int
 *    状态码信息 String
 *    响应数据 Object
 */
public class Result {
    //定义状态码
    public static final int SUCCESS = 1;
    public static final int FAILS = 0;
    public static final int NOLOGIN= 2;
    private int code;
    private String message;
    private Object obj;
    public Result(int code,String message){
        this.code = code;
        this.message = message;
    }
    public Result(int code, String message, Object obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
