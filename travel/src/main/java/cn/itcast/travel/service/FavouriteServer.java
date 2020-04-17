package cn.itcast.travel.service;

public interface FavouriteServer {
    /**
     * 判断是否已收藏线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavourite(int rid, int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     * @return
     */
    void addFavourite(int rid, int uid);
}
