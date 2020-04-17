package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryServer {
    /**
     * 查询所有分类
     * @return
     */
    List<Category> findCategories();
}
