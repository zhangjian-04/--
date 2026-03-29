package com.study.bigevent.mapper;

import com.study.bigevent.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //根据创建人的id来进行新增元素
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    //根据创建人的id来查找元素
    @Select("select * from category where create_user = #{id}")
    List<Category> findByCreateUserId(Integer id);

    //根据主键id来进行查找元素
    @Select("select * from category where id = #{id}")
    List<Category> findById(Integer id);

    @Update("update category set category_name = #{categoryName},category_alias = #{categoryAlias},update_time= now() " +
            "where id = #{id}")
    void updateById(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Integer id);

    //根据主键id来进行修改元素

}
