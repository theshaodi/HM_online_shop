package com.itheima.service.impl;

import com.itheima.dao.ProductDao;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.RedisUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

/**
 * @Project: maven
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-23 17:00
 * @Description:
 * @Version: 1.0
 */
public class ProductServiceImpl implements ProductService {

    private ProductDao PD = BeanFactory.newInstance(ProductDao.class);

    /**
     * 获取热点商品（前九个)
     * @return type List<Product>
     */
    @Override
    public List<Product> findHostProducts() {
        Jedis jd = RedisUtils.getJedis();
        try {
            String hostproducts = jd.get("hostproducts");
            List<Product> hostProductList = null;
            if(hostproducts == null){
                hostProductList = PD.findHostProducts();
                jd.set("hostproducts", JSONArray.fromObject(hostProductList).toString());
            }else{
                JSONArray jsonArray = JSONArray.fromObject(hostproducts);
                hostProductList = (List)JSONArray.toCollection(jsonArray, Product.class);
            }
            jd.close();
            return hostProductList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jd.close();
        return null;
    }

    /**
     * 获取新鲜商品(前九个)
     * @return type List<Product>
     */
    @Override
    public List<Product> findNewProducts() {
        Jedis jd = RedisUtils.getJedis();
        try {
            String newproducts = jd.get("newproducts");
            List<Product> newproductList = null;
            if(newproducts == null){
                newproductList = PD.findNewProducts();
                jd.set("newproducts",JSONArray.fromObject(newproductList).toString());
            }else{
                JSONArray jsonArray = JSONArray.fromObject(newproducts);
                newproductList = (List)JSONArray.toCollection(jsonArray,Product.class);
            }
            jd.close();
            return newproductList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jd.close();
        return null;
    }

    /**
     * 通过某个商品的pid查询该商品的所有信息
     * @param pid 商品id
     * @return type Product
     */
    @Override
    public Product findProductById(String pid) {
        Jedis jd = RedisUtils.getJedis();
        String redis_pid = "product_"+pid;
        try {
            String product = jd.get(redis_pid);
            Product p = null;
            if(product == null){
                p = PD.findProductById(pid);
                jd.set(redis_pid, JSONObject.fromObject(p).toString());
            }else{
                JSONObject jsonArray = JSONObject.fromObject(product);
                p = (Product)JSONObject.toBean(jsonArray,Product.class);
            }
            jd.close();
            return p;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jd.close();
        return null;
    }

    /**
     * 分页查询
     * @param cid 商品类别的cid
     * @param currentPage 前端传来的当前页码,默认为1
     * @param pageSize 每页显示的商品树数量
     * @return 返回类型PageBean<Product>
     */
    @Override
    public PageBean<Product> getProductListById(String cid, int currentPage, int pageSize) {

        PageBean<Product> ppb = new PageBean<>();

        try {
            long totalCount = PD.totalCountByCid(cid);

            List<Product> productList = PD.getProductListById(cid, currentPage, pageSize);

            int totalPage = (int) (Math.ceil(((double) totalCount) / pageSize));

            ppb.setCurrentPage(currentPage);
            ppb.setPageSize(pageSize);
            ppb.setTotalCount(totalCount);
            ppb.setList(productList);
            ppb.setTotalPage(totalPage);
            return ppb;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
