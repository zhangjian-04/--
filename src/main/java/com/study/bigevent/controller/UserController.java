package com.study.bigevent.controller;

import com.study.bigevent.pojo.Result;
import com.study.bigevent.pojo.User;
import com.study.bigevent.service.UserService;
import com.study.bigevent.util.JwtUtil;
import com.study.bigevent.util.Md5Util;
import com.study.bigevent.util.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Validated // 这个是判断传入的参数是不是合法值
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册接口
     * 
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register") // 下面这个是在引入参数的时候来进行判断
    public Result<Void> getUserById(@Pattern(regexp = "^\\S{5,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String password) {

        // 判断一下用户名和密码是不是符合条件 满足才进行注册
        /*
         * 这个是第一个二中自己手动写的格式m
         * if (username.length() < 5 || usernae.length() > 16) {
         * return Result.error("用户名输入不规范，请重新输入！");
         * }
         * if (password.length() < 5 || password.length() > 16) {
         * return Result.error("密码输入不规范，请重新输入！");
         * }
         */

        // 然后就是判断用户名是不是唯一
        User user = userService.findByUserName(username);
        if (user == null) {
            // 如果不存在就直接注册
            userService.register(username, password);

            // 返回成功结果并包含用户数据
            return Result.success();
        }

        // 返回错误信息
        return Result.error("用户名已经存在了,请更换一个用户名！！！");
    }

    /**
     * 实现登录接口
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
            @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 先要判断用户名是否存在
        User u = userService.findByUserName(username);
        // 当这个用户名为空时
        if (u == null) {
            return Result.error("该用户不存在");
        }

        // 这个就是表示存在
        // 存在之后就是要进行判断密码是不是一样，就业需要将其对这个密码进行加密，然后在进行比对

        if (Md5Util.getMD5String(password).equals(u.getPassword())) {
            // 生成一token JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());
            // 生成token
            String token = JwtUtil.genToken(claims);

            // 将这个token存入到这个Redis里面取
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            // 直接存入这个键值对都是自己的 并且设置结束时间
            operations.set(token, token, 1, TimeUnit.HOURS);
            // 直接返回这个token
            return Result.success(token);
        }

        // 如果没有执行上面的就执行这个错误的
        return Result.error("密码输入错误");
    }

    /**
     * 查找用户信息 根据token来进行查找元素
     *
     * @return
     */
    @GetMapping("/userInfo") // 这个Authorization就是http中的参数类型
    public Result<User> userInfo(/* @RequestHeader(name = "Authorization") String token */) {
        /*
         * Authorization这个是表示当前要进行授权才可以来进行下面这个操作，就是要添加token
         * //这个就是存放的元素，里面含有id ，name之类的元素
         * Map<String, Object> map = JwtUtil.parseToken(token);
         * //获取名字
         * String username = (String) map.get("username");
         */

        // 第二种方式，使用ThreadLocal这个类来进行
        Map<String, Object> map = ThreadLocalUtil.get();// 自己编写的ThreadLocalUtil类
        String username = (String) map.get("username");
        // 调用服务层，来获取username
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    /**
     * 跟新用户数据
     * 
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {// @validated写上去实体类定义的才可以生效
        userService.update(user);
        // 成功之后就直接返回相应对象
        return Result.success();
    }

    /**
     * 上传头像
     *
     * @param avatarUrl
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result<Void> updateAvatar(@RequestParam @URL String avatarUrl) {

        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 修改密码
     * 
     * @param params
     * @return
     */
    @PatchMapping("/updatePwd") // 这个就是表示从请求头哪里来获取token
    public Result<Void> updatePwd(@RequestBody Map<String, String> params,
            @RequestHeader("Authorization") String token) {
        // 这个@RequestBody是接收json的响应式数据 是为了接收在Apifox上的元素
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        // 判断这些密码不能为空
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        // 判断输入的密码正不正确 这个是ThreadLocalUtil之前存入的元素
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        // 获取数据库里面的密码
        User user = userService.findByUserName(username);
        String password = user.getPassword();

        // 这里是对加密的密码来进行比较，因为原密码是加密的
        if (!password.equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码输入不正确!");
        }
        if (!newPwd.equals(rePwd)) {
            return Result.error("两次密码输入不一致！");
        }

        // 成功之后就是将新密码来进行加密传入到service层
        userService.updatePwd(Md5Util.getMD5String(newPwd));

        // 删除redis中的token数值
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();

    }
}
