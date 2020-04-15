package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteServer {
    /**
     * 获取PageBean对象
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid获取Route对象
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
