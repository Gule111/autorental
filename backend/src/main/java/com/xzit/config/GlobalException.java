package com.xzit.config;

import com.xzit.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 31507
 */
/**
 * 全局异常处理类
 * 用于捕获全局范围内的异常，以便统一处理和响应
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {
    /**
     * 处理全局异常的方法
     * 当系统中任何地方抛出异常时，该方法将被调用，以处理并返回错误信息
     *
     * @param e 异常对象，包含异常信息
     * @return 返回一个结果对象，表示异常处理的结果
     */
    @ExceptionHandler(value= Exception.class)
    public Result handleException (Exception e) {
        // 记录异常信息，便于调试和追踪问题
        log.error("全局异常捕获：{}", e.getMessage());
         e.printStackTrace();
        // 返回失败结果，表示异常处理的结果
        return Result.fail(e.getMessage());
    }
}
