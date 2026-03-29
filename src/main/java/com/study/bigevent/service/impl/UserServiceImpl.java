package com.study.bigevent.service.impl;

import com.study.bigevent.mapper.UserMapper;
import com.study.bigevent.util.Md5Util;
import com.study.bigevent.pojo.User;
import com.study.bigevent.service.UserService;
import com.study.bigevent.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service //服务层
public class UserServiceImpl implements UserService {

    //注入一个mapper层，来超控数据库
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        //先对密码来进行加密
        String md5String = Md5Util.getMD5String(password);

        //然后在进行注册
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        //对修改的时间设置位现在修改的时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        //要在ThreadLocalUtil上获取id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String md5String) {
        //要在ThreadLocalUtil上获取id
        Map<String ,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(md5String, id);
    }
}

