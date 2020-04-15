package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryServer;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ：Hzhang
 * @date ：Created in 2020/4/12 15:56
 * @description：
 * @modified By：
 * @version: $
 */
public class CategoryServerImpl implements CategoryServer {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();
    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<Category> findCategories() {
         // 1.先从缓存中查询
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
         // 2.缓存中没有则从数据库中查询，并将结果存入缓存
        if(categories == null || categories.size() == 0){
            List<Category> categoryList = categoryDao.findCategories();
            for (Category c : categoryList) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
            return categoryList;
        }
        else{
            // 3.缓存中有数据，转换为List并返回
            List<Category> categoryList = new ArrayList<>();
            for (Tuple tuple : categories) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                categoryList.add(category);
            }
            return categoryList;
        }
    }
}
