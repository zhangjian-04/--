package com.study.bigevent.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.study.bigevent.mapper.ArticleMapper;
import com.study.bigevent.pojo.Article;
import com.study.bigevent.pojo.PageBean;
import com.study.bigevent.service.ArticleService;
import com.study.bigevent.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceIml implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addByCreateId(Article article) {
        //这里要先有修改之前的数据
        //时间  这个可以直接在这里写，也可以在mapper层哪里写
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        //获取登录人的信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        //调用这个mapper层
        articleMapper.addByCreateId(article);
    }

    @Override
    public void updateById(Article article) {
        //更新时间
        article.setUpdateTime(LocalDateTime.now());

        articleMapper.updateById(article);
    }

    @Override
    public List<Article> getDataById(Integer id) {
        return articleMapper.getDateById(id);
    }

    @Override
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //床架你一个PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //开启这个pageHelper
        PageHelper.startPage(pageNum, pageSize);

        //调用这个mapper层
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        //将其进行强壮为PageBean对象
        List<Article> as = articleMapper.list(userId, categoryId, state);
        Page<Article> p = (Page<Article>) as;

        //然后在将数据存入到这个PageBean对象里面去
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
