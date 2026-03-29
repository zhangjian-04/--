package com.study.bigevent.config;

import com.study.bigevent.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器
@Configuration // 注入类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 设置一个拦截器 登录和注册接口不进行拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");// 这个就是表示当前去除这两个登录的路径
    }

}
