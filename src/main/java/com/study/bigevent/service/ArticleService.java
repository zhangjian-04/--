package com.study.bigevent.service;

import com.study.bigevent.pojo.Article;
import com.study.bigevent.pojo.PageBean;

import java.util.List;

public interface ArticleService {
    //根据创建人的id来添加元素
    void addByCreateId(Article article);

    //根据主键id来更新元素
    void updateById(Article article);

    //根据主键id来查找文章
    List<Article> getDataById(Integer id);

    //根据主键id来进行删除元素
    void deleteById(Integer id);

    //分页查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
