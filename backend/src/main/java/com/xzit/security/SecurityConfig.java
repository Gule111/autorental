package com.xzit.security;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author 31507
 */
// 定义配置类，指示Spring应用上下文，该类可以提供配置信息
@Configuration
// 启用Web安全性，包括自动配置安全过滤器链、认证管理器等
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailHandler loginFailHandler;

    @Resource
    private CustomerUserDetailService customerUserDetailService;
    @Resource
    private CustomerAccessDeniedHandler customerAccessDeniedHandler;

    @Resource
    private VerifyTokenFilter verifyTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomerAnonymousEntryPoint customerAnonymousEntryPoint) throws Exception {
//   // 在UsernamePasswordAuthenticationFilter之前添加验证Token的过滤器，以进行登录前的过滤操作
        http.addFilterBefore(verifyTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //登录处理过程
        http.formLogin()
                .loginProcessingUrl("/auto/user/login") // 设置登录处理URL
                .successHandler(loginSuccessHandler) // 设置登录成功处理器
                .failureHandler(loginFailHandler) // 设置登录失败处理器
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 设置会话创建策略为无状态
                .and()
                .authorizeHttpRequests() // 授权请求配置
                .requestMatchers("/auto/user/login") // 匹配登录请求
                .permitAll() // 允许所有请求访问
                .anyRequest().authenticated() // 任何其他请求需要认证
                .and()
                .exceptionHandling() // 异常处理配置
                .authenticationEntryPoint(customerAnonymousEntryPoint) // 设置未认证入口点
                .accessDeniedHandler(customerAccessDeniedHandler) // 设置访问拒绝处理器
                .and()
                .cors() // 跨域配置
                .and()
                .csrf().disable() // 关闭CSRF保护  跨站请求伪造 是一种网络攻击
                .userDetailsService(customerUserDetailService); // 设置用户详情服务
        return http.build(); // 构建并返回安全过滤链

    }
}
