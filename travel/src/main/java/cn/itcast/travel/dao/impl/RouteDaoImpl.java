package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/12 18:06
 * @description：
 * @modified By：
 * @version: $
 */
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 获取总记录数
     * @param cid
     * @return
     */
    @Override
    public int count(int cid, String rname) {
        StringBuilder sql = new StringBuilder("select count(*) from tab_route where 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if(cid != 0){
            sql.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() != 0 && !"null".equals(rname)){
            sql.append(" and rname like ?");
            params.add("%"+ rname +"%");
        }
        return template.queryForObject(sql.toString(), Integer.class, params.toArray());
    }

    /**
     * 获取当前页的记录
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> pageList(int cid, int start, int pageSize, String rname) {
        StringBuilder sql = new StringBuilder("select * from tab_route where 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if(cid != 0){
            sql.append(" and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() != 0 && !"null".equals(rname)){
            sql.append(" and rname like ?");
            params.add("%"+ rname +"%");
        }
        sql.append(" limit ?, ?");
        params.add(start);
        params.add(pageSize);
        return template.query(sql.toString(), new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
