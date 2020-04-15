package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {
    /**
     * 根据rid从tab_route_img表中获取到含多个图片对象的列表
     * @param rid
     * @return
     */
    List<RouteImg> findRouteImageList(int rid);
}
