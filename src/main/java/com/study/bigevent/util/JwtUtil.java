package com.study.bigevent.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "study";
	
	//接收业务数据,生成token并返回
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)//添加负载 添加元素
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))//设置结束时间为一个小时
                .sign(Algorithm.HMAC256(KEY));//设置加密算法，并且将这个key这个数值传入进去
    }

	//接收token,验证token,并返回业务数据
    public static Map<String, Object> parseToken(String token) {
        //解析和验证这个token
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
