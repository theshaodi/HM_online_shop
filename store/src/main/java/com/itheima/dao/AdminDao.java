package com.itheima.dao;

import com.itheima.domain.Category;
import com.itheima.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: com.itheima.dao
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-28 20:38
 * @Description:
 * @Version: 1.0
 */
public interface AdminDao {
    // 查询所有的商品分类
    List<Category> findAllCategory() throws SQLException;
    // 增加商品分类
    void addCategory(String cid,String cname) throws SQLException;
    // 根据商品分类cid删除该商品分类
    void deleteCategoryById(String cid) throws SQLException;
    // 根据商品分类cid查询该商品分类的数据
    Category getCategoryByCid(String cid) throws SQLException;

    /**
     * 根据cid更新某个商品分类信息
     * @param c 由web层根据前端数据构建的Category对象
     * @throws SQLException
     */
    void updateCategoryByCid(Category c) throws SQLException;

    User loginAdmin(String uname,String password) throws SQLException;
}
