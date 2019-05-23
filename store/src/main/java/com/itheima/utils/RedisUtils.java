package com.itheima.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/*
   数据库连接池工具类
      目的 获取 连接对象
      完成配置

 */
public class RedisUtils {

    private static JedisPool pool = null;

    static{
        ResourceBundle bundle = ResourceBundle.getBundle("redisdb");
        // 解析  获取ip
        String host = bundle.getString("host");
        int port = Integer.parseInt(bundle.getString("port"));
        int maxTotal = Integer.parseInt(bundle.getString("maxTotal"));
        int maxIdle = Integer.parseInt(bundle.getString("maxIdle"));

        // //1:创建连接池配置项对象
        JedisPoolConfig config = new JedisPoolConfig();
        //2:设置配置项
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);

        //3:创建连接池对象
        pool = new JedisPool(config, host, port);
    }

    public static Jedis getJedis(){

        return pool.getResource();
    }
}
