package com.study.bigevent.service.impl;

import com.study.bigevent.mapper.CategoryMapper;
import com.study.bigevent.pojo.Category;
import com.study.bigevent.service.CategoryService;
import com.study.bigevent.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service //将这个交给service来进行管理
public class CategoryServiceIml implements CategoryService {

    //注入一个mapping层
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //需要先进性补充属性值
        //时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        //创建人的信息  在拦截器里面来查找用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        //获取这个id
        Integer userid = (Integer) map.get("id");//这个id是user这里面的id
        //修改这个创建人的信息  直接传入这个id 因为这个创建人的外键是绑定到这个id
        category.setCreateUser(userid);
        //接下来在去调用这个mapper层
        categoryMapper.add(category);
    }

    @Override
    public List<Category> findByCreateUserId() {
        //需要获取到这个id，因为是根据id来进行查找元素
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        return categoryMapper.findByCreateUserId(id);
    }

    @Override
    public List<Category> findById(Integer id) {
        return categoryMapper.findById(id);
    }

    @Override
    public void updateById( Category category) {
        //更新用数据是需要将网页上写的json数据以category的形式来进行传输过来
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
