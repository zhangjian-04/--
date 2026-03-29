package com.study.bigevent.service;

import com.study.bigevent.pojo.Category;

import java.util.List;

public interface CategoryService {
    //根据创建用户id来进行查找元素
    void add(Category category);

    //根据创建者的id来进行查找元素
    List<Category> findByCreateUserId();

    //根据主键id来进行查找元素
    List<Category> findById(Integer id);

    //根据id来进行修改元素
    void updateById(Category category);

    //根据主键id来进行删除元素
    void deleteById(Integer id);
}

