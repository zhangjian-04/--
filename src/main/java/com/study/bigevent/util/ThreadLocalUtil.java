package com.study.bigevent.util;

/**
 * 使用存储数据时：set()和get()方法
 * 使用ThreadLocal存储数据，线程更见安全
 * 使用玩记得使用remove方法来清楚数据
 */

/**
 * ThreadLocal 工具类
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    //提供ThreadLocal对象,
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    //根据键获取值
    public static <T> T get(){
        return (T) THREAD_LOCAL.get(); //这个表示可以进行转化为所有类型
    }
	
    //存储键值对
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }


    //清除ThreadLocal 防止内存泄漏
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
