package com.study.bigevent.interceptors;

import com.study.bigevent.util.JwtUtil;
import com.study.bigevent.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

// 使用redis处理token
@Component //这个时把这个当前拦截的对象注入到ioc容器里面
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String token =request.getHeader("Authorization");
        //接下来就是验证这个token
        try {
            //获取redis这里面中的相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String RedisToken = operations.get(token);
            if(RedisToken == null){
                //这个就是表示token已经失效了  直接抛出运行异常，跳转到catch这里面取
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //如果是直接执行这个就是表示当前已经校验成功

            //将业务数据传入ThreadLocal这个类里面去
            ThreadLocalUtil.set(claims);

            return true;
        } catch (Exception e) {
            //返回http中的响应码
            response.setStatus(401);
            return false;
        }
    }

    //再写一个方法，用来清空ThreadLocalUtil使用的数据，防止内存进行泄露
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
