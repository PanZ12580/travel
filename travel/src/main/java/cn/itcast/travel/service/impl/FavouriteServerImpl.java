package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.dao.impl.FavouriteDaoImpl;
import cn.itcast.travel.service.FavouriteServer;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/14 15:09
 * @description：
 * @modified By：
 * @version: $
 */
public class FavouriteServerImpl implements FavouriteServer {
    FavouriteDao favouriteDao = new FavouriteDaoImpl();
    /**
     * 判断是否已收藏线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavourite(int rid, int uid) {
        return favouriteDao.isFavourite(rid, uid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public void addFavourite(int rid, int uid) {
        favouriteDao.addFavorite(rid, uid);
    }
}
