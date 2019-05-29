package com.itheima.service;

import com.itheima.domain.Category;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.exception.DeleteCategoryException;

import java.util.List;

/**
 * @Project: com.itheima.service
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-28 20:43
 * @Description:
 * @Version: 1.0
 */
public interface AdminService {
    List<Category> findAllCategory();
    boolean addCategory(String cname);
    boolean deleteCategoryByCid(String cid) throws DeleteCategoryException;
    Category getCategoryByCid(String cid);
    boolean updateCategoryByCid(Category c);
    User loginAdmin(String uname,String password);

    /**
     * 分页查询商品按时间倒序排列
     * @param currentPage 当前页码
     * @param pageSize 每页展示的商品数量
     * @return 返回封装好的PageBean对象
     */
    PageBean<Product> findProductsByPage(int currentPage,int pageSize);

    /**
     * 向数据库中添加商品
     * @param product 封装好的商品对象
     * @return 成功添加进数据库返回true,否则返回false
     */
    boolean addProduct(Product product);
}
