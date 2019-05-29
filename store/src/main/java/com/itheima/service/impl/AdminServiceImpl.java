package com.itheima.service.impl;

import com.itheima.dao.AdminDao;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.User;
import com.itheima.exception.DeleteCategoryException;
import com.itheima.service.AdminService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: com.itheima.service.impl
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-28 20:43
 * @Description:
 * @Version: 1.0
 */
public class AdminServiceImpl implements AdminService {

    private AdminDao AD = BeanFactory.newInstance(AdminDao.class);
    private ProductDao PD = BeanFactory.newInstance(ProductDao.class);

    @Override
    public List<Category> findAllCategory() {
        try {
            return AD.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addCategory(String cname) {
        try {
            AD.addCategory(UUIDUtils.getUUID(),cname);
            clearCategorysFromRedis();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clearCategorysFromRedis(){
        Jedis jd = new Jedis();
        jd.del("categorys");
        jd.close();
    }

    @Override
    public boolean deleteCategoryByCid(String cid) throws DeleteCategoryException{
        try {
            long l = PD.totalCountByCid(cid);
            if(l > 0){
                throw new DeleteCategoryException("该商品分类下包含有"+l+"个商品,无法直接删除该商品分类");
            }
            AD.deleteCategoryById(cid);
            clearCategorysFromRedis();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Category getCategoryByCid(String cid) {
        try {
            return AD.getCategoryByCid(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCategoryByCid(Category c) {
        try {
            AD.updateCategoryByCid(c);
            clearCategorysFromRedis();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User loginAdmin(String uname, String password) {
        try {
            return AD.loginAdmin(uname,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
