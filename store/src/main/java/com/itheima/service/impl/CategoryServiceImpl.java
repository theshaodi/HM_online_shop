package com.itheima.service.impl;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 15:37
 * @Description: 商品分类service接口实现类
 * @Version: 1.0
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao CD = BeanFactory.newInstance(CategoryDao.class);

    @Override
    public List<Category> findAll() {
        try {
            Jedis jd = new Jedis();
            String categorys= jd.get("categorys");
            List<Category> categoryList = null;
            if(categorys == null){
                System.out.println("走数据库");
                categoryList = CD.findAll();
                jd.set("categorys",JSONArray.fromObject(categoryList).toString());
                jd.close();
            }else{
                System.out.println("走redis");
                JSONArray jsonArray = JSONArray.fromObject(categorys);
                categoryList = (List) JSONArray.toCollection(jsonArray, Category.class);
            }
            return categoryList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
