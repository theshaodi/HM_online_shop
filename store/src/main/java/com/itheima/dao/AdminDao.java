package com.itheima.dao;

import com.itheima.domain.Category;

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
}
