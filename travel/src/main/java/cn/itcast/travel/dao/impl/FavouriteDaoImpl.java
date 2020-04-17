package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavouriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/14 15:11
 * @description：
 * @modified By：
 * @version: $
 */
public class FavouriteDaoImpl implements FavouriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 判断是否已收藏线路
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavourite(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
        }
        return favorite != null;
    }

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    @Override
    public int countByRid(String rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        return template.queryForObject(sql, Integer.class, Integer.parseInt(rid));
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?, ?, ?)";
        template.update(sql, rid, new Date(), uid);
    }
}
