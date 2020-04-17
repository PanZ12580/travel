package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavouriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteServer;

import java.util.List;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/12 18:27
 * @description：
 * @modified By：
 * @version: $
 */
public class RouteServerImpl implements RouteServer {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavouriteDao favouriteDao = new FavouriteDaoImpl();
    /**
     * 获得PageBean对象
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int start = (currentPage - 1)*pageSize;
        List<Route> pageList = routeDao.pageList(cid, start, pageSize, rname);

        int count = routeDao.count(cid, rname);
        int totalPage = (int)Math.ceil(((double)count)/pageSize);

        pb.setTotalPage(totalPage);
        pb.setCount(count);
        pb.setPageList(pageList);
        return pb;
    }

    /**
     * 获得一个详细Route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        Route route = routeDao.findOne(Integer.parseInt(rid));
        List<RouteImg> routeImageList = routeImageDao.findRouteImageList(Integer.parseInt(rid));
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setRouteImgList(routeImageList);
        route.setSeller(seller);

        int favoriteCount = favouriteDao.countByRid(rid);
        route.setCount(favoriteCount);
        return route;
    }
}
