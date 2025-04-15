package com.kains.config;

import com.kains.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kains
 * @date 2021/12/10
 */


/**
 * 此类用于配置Spring MVC的相关配置
 * 添加拦截器、视图解析器、静态资源映射、消息转换器等
 * 配置要排出的路径
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需要 排除的路径
        List<String> toExclude=new ArrayList<>();
        //swagger
        toExclude.add("/swagger-ui.html");
        toExclude.add("v3/api-docs");
        toExclude.add("/swagger-ui/**");

        //用户登录与注册
        toExclude.add("/v1/login");
        toExclude.add("/v1/register");


        //配置拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(toExclude);
    }
}
