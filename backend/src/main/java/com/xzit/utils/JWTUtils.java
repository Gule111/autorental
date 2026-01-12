package com.xzit.utils;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 31507
 */
@Component
public class JWTUtils {
    public static final String SECRET_KEY = "Gule";

    public static final long EXPIRE_TIME = 1000L * 60 * 30;

    public static String createToken(Map<String, Object> payload) {
        DateTime now = new DateTime();
//        过期时间
        DateTime newTime = new DateTime(now.getTime() + EXPIRE_TIME);
//        设置签发时间
        payload.put(cn.hutool.jwt.JWTPayload.ISSUED_AT, now);
//        设置过期时间
        payload.put(cn.hutool.jwt.JWTPayload.EXPIRES_AT, newTime);
//      生效时间
        payload.put(cn.hutool.jwt.JWTPayload.NOT_BEFORE, now);
        //hutool的jwt工具类
        return JWTUtil.createToken( payload, SECRET_KEY.getBytes());
    }

    public static JWTPayload parseToken(String token){
        JWT jwt=JWTUtil.parseToken(token);
        if(!jwt.setKey( SECRET_KEY.getBytes()).verify()){
            throw new RuntimeException("token异常");
        }
        if(!jwt.validate(0)){
             throw new RuntimeException("token已过期");
        }
        return jwt.getPayload();
    }

    public static void main(String[] args) {
//        Map<String,Object> map=new HashMap <>();
//        map .put("username","admin");
//         map .put("password","123456");
//        String token = createToken(map);
//         System.out.println(token);
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsIm5iZiI6MTc0ODgzNjYxMCwiZXhwIjoxNzQ4ODM4NDEwLCJpYXQiOjE3NDg4MzY2MTAsInVzZXJuYW1lIjoiYWRtaW4ifQ.iYGLu06xV3dbeQl9VtthEIolVfTsXBWnU6g3wDcXdjE";
         JWTPayload parseToken = parseToken(token);
        Object username = parseToken.getClaim("username");
        System.out.println("username = " + username);
        NumberWithFormat claim =(NumberWithFormat) parseToken.getClaim(JWTPayload.EXPIRES_AT);
        DateTime dateTime=(DateTime) claim.convert( DateTime.class,claim);
        long nowTime=DateTime.now().getTime();
        long expireTime=dateTime.getTime();
        System.out.println(
                "还有"+ (expireTime-nowTime)/1000+"秒过期"
        );

    }
}
