package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/13 15:34
 * @description：
 * @modified By：
 * @version: $
 */
public class RouteImageDaoImpl implements RouteImageDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据rid从tab_route_img表中获取到含多个图片对象的列表
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findRouteImageList(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
