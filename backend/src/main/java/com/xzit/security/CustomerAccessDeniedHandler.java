package com.xzit.security;


import com.alibaba.fastjson.JSON;
import com.xzit.utils.Result;
import com.xzit.utils.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户无权访问，被拒绝的处理器
 */
/**
 * 自定义访问拒绝处理器
 * 用于处理当用户尝试访问未授权的资源时的情况
 * 实现了AccessDeniedHandler接口，以定制化处理访问拒绝逻辑
 */
@Component
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 处理访问拒绝事件
     * 当访问拒绝发生时，这个方法会被调用，并在这里处理异常
     *
     * @param request          当前请求对象，包含请求信息
     * @param response         响应对象，用于向客户端发送响应
     * @param accessDeniedException  访问拒绝异常，包含异常信息
     * @throws IOException           如果输出流操作失败
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws  IOException {
        // 设置响应内容类型和字符集
        response.setContentType("application/json;charset=utf-8");
        // 获取响应输出流，用于写入响应数据
        ServletOutputStream outputStream = response.getOutputStream();
        // 创建并序列化结果对象为JSON字符串，表示访问拒绝的结果信息
        String result = JSON.toJSONString(Result.fail().setCode(ResultCode.UNAUTHORIZED)
                .setMessage("无权访问"));
        // 将结果字符串以UTF-8编码写入响应输出流
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        // 刷新输出流，确保数据被发送
        outputStream.flush();
        // 关闭输出流
        outputStream.close();
    }
}

