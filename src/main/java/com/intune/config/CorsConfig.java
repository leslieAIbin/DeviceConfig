package com.intune.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {
    public CorsConfig() {
    }


    @Bean
    public CorsFilter corsFilter() {
        //1.添加 cors跨域配置信息
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("*");
        config.setAllowedOriginPatterns(allowedOriginPatterns);
        //设置是否能发送cookie信息
        config.setAllowCredentials(true);

        //设置运行请求的方式如get post
        config.addAllowedMethod("*");
        //设置运行的header
        config.addAllowedHeader("*");

        //2.为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);

        //3.返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }
}