package com.fandou.coffee.learning.springcloud.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class}) // 去掉自动生成随机密码
@EnableEurekaClient
public class OAuth2ClientApp {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2ClientApp.class,args);
    }
}
