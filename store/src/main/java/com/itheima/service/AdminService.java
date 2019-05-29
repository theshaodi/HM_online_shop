package com.itheima.service;

import com.itheima.domain.Category;
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
}
