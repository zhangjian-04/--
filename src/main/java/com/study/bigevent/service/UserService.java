package com.study.bigevent.service;

import com.study.bigevent.pojo.User;

public interface UserService {
    //这个是判断用户名是不是重复
    User findByUserName(String username);

    //这个是注册用户
    void register(String username, String password);

    //更新用户信息
    void update(User user);


    //上传头像
    void updateAvatar(String avatarUrl);

    //修改密码
    void updatePwd(String md5String);
}
