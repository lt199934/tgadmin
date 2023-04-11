package com.tg.admin.config;

import com.tg.admin.common.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Program: admin
 * @ClassName InterceptorConfig
 * @Author: liutao
 * @Description: 拦截器配置
 * @Create: 2023-03-14 06:19
 * @Version 1.0
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**","/file/page")
                .excludePathPatterns("/user/login","/user/register","/menu/**","/captcha/**","/*/export","/*/import","/file/**","https://v1.hitokoto.cn/?c=f&encode=text")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**","/doc.html");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
