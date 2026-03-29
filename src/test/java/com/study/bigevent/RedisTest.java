package com.study.bigevent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest //吐过添加这个就是会在初始化是先去初始化spring这个容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加元素
    @Test
    public void TestSet(){
        //调用这个opsForValue()这个Set函数
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("username","lisi");
        //按照一个添加时间来获取的，有过期效应
        operations.set("id","2",10, TimeUnit.SECONDS);
    }

    //获取元素
    @Test
    public void  TestGet(){
        //也可以直接在cmd输入
        //docker exec -it my-redis redis-cli
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));
        System.out.println(operations.get("id"));
    }

}
