package com.itheima.service.impl;

import com.itheima.dao.AdminDao;
import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.exception.DeleteCategoryException;
import com.itheima.service.AdminService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import redis.clients.jedis.Jedis;
import sun.jvm.hotspot.debugger.Page;

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

    /**
     * 分页查询商品按时间倒序排列
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Product> findProductsByPage(int currentPage, int pageSize) {
        PageBean<Product> ppb = new PageBean<>();

        try {
            Long productCount = AD.getProductCount();
            List<Product> products = AD.findProductsByPage(currentPage, pageSize);
            int totalPage = (int) Math.ceil(((double) productCount) / pageSize);
            ppb.setTotalPage(totalPage);
            ppb.setCurrentPage(currentPage);
            ppb.setList(products);
            ppb.setPageSize(pageSize);
            ppb.setTotalCount(productCount);
            return ppb;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向数据库中添加商品
     *
     * @param product 封装好的商品对象
     * @return 成功添加进数据库返回true, 否则返回false
     */
    @Override
    public boolean addProduct(Product product) {
        try {
            AD.addProduct(product);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
