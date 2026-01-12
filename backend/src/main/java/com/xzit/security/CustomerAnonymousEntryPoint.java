package com.xzit.security;


import com.alibaba.fastjson.JSON;
import com.xzit.utils.Result;
import com.xzit.utils.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 匿名用户访问无权限资源处理器
/**
 * 实现AuthenticationEntryPoint接口，用于处理未认证的访问请求
 * 该类主要用于处理那些没有通过Spring Security认证的用户访问受保护资源时的入口点
 */
@Component
public class CustomerAnonymousEntryPoint implements AuthenticationEntryPoint {
    /**
     * 处理未认证的访问请求
     * 当用户尝试访问需要认证的资源但未提供有效认证信息时，此方法将被调用
     *
     * @param request       HTTP请求对象，包含请求信息
     * @param response      HTTP响应对象，用于向客户端发送响应
     * @param authException 认证异常，表示认证过程中遇到的异常
     * @throws IOException      如果在处理过程中发生I/O错误
     * @throws ServletException 如果在处理过程中发生Servlet错误
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // 设置响应内容类型为JSON，并确保字符集为UTF-8
        response.setContentType("application/json;charset=utf-8");
        // 获取响应的输出流，用于向客户端发送响应数据
        ServletOutputStream outputStream = response.getOutputStream();
        // 构建并发送一个表示未授权的JSON响应
        String result= JSON.toJSONString(Result.fail().setCode(ResultCode.UNAUTHORIZED)
                .setMessage("匿名用户无权访问!"));
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        // 确保响应数据被正确写入并关闭输出流
        outputStream.flush();
        outputStream.close();
    }
}
