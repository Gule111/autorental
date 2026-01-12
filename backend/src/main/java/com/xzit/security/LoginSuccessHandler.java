package com.xzit.security;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTPayload;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xzit.entity.User;
import com.xzit.utils.JWTUtils;
import com.xzit.utils.RedisUtils;
import com.xzit.utils.ResultCode;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 31507
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
// 设置响应内容类型为JSON格式，编码为UTF-8
        response.setContentType("application/json;charset=utf-8");

// 获取用户信息，其中User类型需要解码认证信息的主体
        User user = (User) authentication.getPrincipal();

// 生成token处理
// 创建一个映射，用于存储用户信息，包括用户名和用户ID
        Map<String, Object> map = new HashMap<>() {{
            put("username", user.getUsername());
            put("userid", user.getId());
        }};

// 使用用户信息创建JWT token
        String token = JWTUtils.createToken(map);

// 解析token以获取过期时间
        NumberWithFormat claim = (NumberWithFormat) JWTUtils.parseToken(token).getClaim(JWTPayload.EXPIRES_AT);
// 将过期时间转换为毫秒
        long time = Convert.toDate(claim).getTime();

// 创建认证结果对象，包含用户ID、成功状态码、token和过期时间
        AuthenticationResult authenticationResult = new AuthenticationResult(user.getId(), ResultCode.SUCCESS, token, time);
// 将认证结果转换为JSON字符串，避免循环引用
        String result = JSON.toJSONString(authenticationResult, SerializerFeature.DisableCircularReferenceDetect);
// 获取响应的输出流，准备写入JSON字符串
        ServletOutputStream outputStream = response.getOutputStream();
// 写入JSON字符串到输出流
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
// 刷新输出流确保数据被写入
        outputStream.flush();
// 关闭输出流
        outputStream.close();

// 构造token的Redis存储键
        String tokenKey = "token:" + token;
// 获取当前时间戳
        long nowTime = DateTime.now().getTime();

// 在Redis中存储token，有效期为token过期时间减去当前时间
        redisUtils.set(tokenKey, token, (time - nowTime) / 1000);
    }


}
