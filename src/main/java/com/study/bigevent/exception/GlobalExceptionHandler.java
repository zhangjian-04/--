package com.study.bigevent.exception;

import com.study.bigevent.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //指定异常
    public Result handleException(Exception e){
        //输入异常信息到控制台
        e.printStackTrace();

        //判断有没有错误的原因，如果有就直接返回错误的原因，没有就返回操作失败
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
