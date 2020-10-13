package com.fandou.coffee.learning.springcloud.admin.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 跨域配置类
 */
@Configuration
public class CorsConfig {

    /**
     * 跨域配置源
     * 
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        // 跨域配置对象
        CorsConfiguration configuration = new CorsConfiguration();

        // 允许跨域调用的源域名：设置为*，表示允许所有域名的主机调用
        configuration.setAllowedOrigins(Arrays.asList(
                "https://www.fandou.com"
                ,"https://fandou.com"
                ,"https://www.chengshangqian.com"
                ,"https://chengshangqian.com")
        );

        // 允许调用HTTP方法：设置为*，表示允许所有HTTP方法的调用
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));

        // 是否允许携带cookie凭据
        configuration.setAllowCredentials(true);

        // 基于URL的跨域配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 注册允许跨域调用的本地服务路径：可调用多次注册方法注册多个路径
        source.registerCorsConfiguration("/api/**",configuration);

        return source;
    }
}
