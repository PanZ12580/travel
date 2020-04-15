package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 获取总记录数
     * @param cid
     * @return
     */
    int count(int cid, String rname);

    /**
     * 获取当前页的记录
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> pageList(int cid, int start, int pageSize, String rname);

    Route findOne(int rid);
}
