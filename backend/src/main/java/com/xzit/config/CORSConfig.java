package com.xzit.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 31507
 */
@Component
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许所有来源的跨域请求
                .allowedOriginPatterns("*")
                //允许所有请求头
                .allowedHeaders("*")
                //允许所有请求方法
                .allowedMethods("*")
                //允许请求携带认证信息 (如cookie)
                .allowCredentials(true)
                //跨域请求的缓存时间
                .maxAge(3600);
    }
}
