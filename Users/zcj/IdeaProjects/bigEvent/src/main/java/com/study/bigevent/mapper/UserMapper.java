package com.study.bigevent.mapper;

import com.study.bigevent.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectUserById(Long id);
}