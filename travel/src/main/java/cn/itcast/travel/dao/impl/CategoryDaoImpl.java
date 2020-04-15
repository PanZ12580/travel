package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/12 15:50
 * @description：
 * @modified By：
 * @version: $
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findCategories() {
        String sql = "select * from tab_category";
        return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
