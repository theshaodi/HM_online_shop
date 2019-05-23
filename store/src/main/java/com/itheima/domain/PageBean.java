package com.itheima.domain;

import java.util.List;

/**
 * @author ：seanyang
 * @date ：Created in 2019/3/26 16:18
 * @description ：分页实体类
 * @version: 1.0
 */
public class PageBean<T> {
    // 当前页数
    private int currentPage;
    // 总记录
    private long totalCount;
    // 总页数
    private int totalPage;
    // 每页显示条数
    private int pageSize;
    // 分页中的数据,集合数据类型
    private List<T> list;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
