package cn.itcast.travel.dao;

public interface FavouriteDao {
    /**
     * 判断用户是否已收藏线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavourite(int rid, int uid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int countByRid(String rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);
}
