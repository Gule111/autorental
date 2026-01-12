package com.xzit.security;

import com.alibaba.fastjson.JSON;
import com.xzit.utils.Result;
import com.xzit.utils.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 31507
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        int code= ResultCode.ERROR;
        String msg=null;
        if(exception instanceof AccountExpiredException){
            code=ResultCode.UNAUTHENTICATED;
            msg="账户过期";
        }else if(exception instanceof BadCredentialsException){
            code=ResultCode.UNAUTHORIZED;
            msg="用户名或密码错误";
        }else if(exception instanceof DisabledException){
            code=ResultCode.UNAUTHORIZED;
            msg="账号被锁定";
        }else if(exception instanceof LockedException){
            code=ResultCode.UNAUTHORIZED;
            msg="账号被禁用";
        }else  if(exception instanceof InternalAuthenticationServiceException){
            code=ResultCode.UNAUTHORIZED;
            msg="账号不存在";
        }else if(exception instanceof CustomerAuthenticationException){
            code=ResultCode.UNAUTHORIZED;
            msg=exception.getMessage();
        }
        String result= JSON.toJSONString(Result.fail().setCode(code).setMessage(msg));
        outputStream.write(result.getBytes("UTF-8"));
    }
}
