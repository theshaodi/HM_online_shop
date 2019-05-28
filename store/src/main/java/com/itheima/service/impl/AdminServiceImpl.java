package com.itheima.service.impl;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Category;
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
    public boolean deleteCategoryByCid(String cid) {
        try {
            AD.deleteCategoryById(cid);
            clearCategorysFromRedis();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
