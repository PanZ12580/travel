package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/13 15:36
 * @description：
 * @modified By：
 * @version: $
 */
public class SellerDaoImpl implements SellerDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据sid从tab_seller表中获得对应的商家对象
     * @param sid
     * @return
     */
    @Override
    public Seller findSeller(int sid) {
        String sql = "select * from tab_seller where sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }
}
