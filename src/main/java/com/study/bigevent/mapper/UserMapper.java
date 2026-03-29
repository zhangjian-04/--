package com.study.bigevent.mapper;

import com.study.bigevent.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper //添这个数据库关联的注解
public interface UserMapper {
    //根据用户名来查询用户
    @Select("select * from user where username = #{username}")
    User findByUserName(String username);


    //添加元素
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values(#{username},#{password},now(),now())")
    void add(String username, String password);

    //根据id来进行修改元素
    @Update("update user set nickname = #{nickname},email= #{email},update_time = #{updateTime} where id = #{id}")
    void update(User user);

    @Update("update user set user_pic = #{avatarUrl} , update_time = now() where id = #{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password = #{md5String} ,update_time = now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
