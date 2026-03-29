package com.study.bigevent;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test //这个是生成JWT令牌
    public void testGen() {

        //创建一个map集合用来传参
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");

        String token = JWT.create()
                .withClaim("user", claims)//添加载荷，添加元素
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//设置过期时间是一小时
                .sign(Algorithm.HMAC256("study"));//指定算法，设置密钥
        System.out.println(token);
    }

 /*   @Test //验证JWT令牌
    public void testParse() {
        //这个是赋值上面的jwt令牌
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VybmFtZSI6eyJpZCI6MSwidXNlcm5hbWUiOiLlvKDkuIkifSwiZXhwIjoxNzQ1MjI4MDYwfQ" +
                ".aDCDHYYKFB5jh1H0sUZiJuOj0MOQFuVMjTePmwi5Sco";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("study")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证token，生成一个解析后的JWT对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("username"));
    }*/
}