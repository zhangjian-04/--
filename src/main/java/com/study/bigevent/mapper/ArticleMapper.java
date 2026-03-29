package com.study.bigevent.mapper;

import com.study.bigevent.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addByCreateId(Article article);

    @Update("update article set title = #{title} ,content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} " +
            "where id = #{id} ")
    void updateById(Article article);

    @Select("select * from article where id = #{id}")
    List<Article> getDateById(Integer id);

    @Delete("delete from article where id = #{id}")
    void deleteById(Integer id);

    //使用动态sql来进行分页查找
    List<Article> list(Integer userId, Integer categoryId, String state);
}
