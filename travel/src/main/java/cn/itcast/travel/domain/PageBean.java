package cn.itcast.travel.domain;

import java.util.List;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/12 18:02
 * @description：
 * @modified By：
 * @version: $
 */
public class PageBean<T> {
    private int currentPage;// 当前页码
    private int totalPage;// 总页数
    private int count;// 总记录数
    private int pageSize;// 每页记录数
    private List<T> pageList;// 当前页面记录

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", count=" + count +
                ", pageSize=" + pageSize +
                ", pageList=" + pageList +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
