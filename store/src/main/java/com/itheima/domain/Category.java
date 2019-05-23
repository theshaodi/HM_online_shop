package com.itheima.domain;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/26 13:47
 * @description ：分类实体类
 * @version: 1.0
 */
public class Category {
    private String cid;    // 分类ID
    private String cname;  // 分类名称

    @Override
    public String toString() {
        return "Category{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
