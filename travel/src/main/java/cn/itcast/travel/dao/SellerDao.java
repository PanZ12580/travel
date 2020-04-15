package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据sid从tab_seller表中获得对应的商家对象
     * @param sid
     * @return
     */
    Seller findSeller(int sid);
}
