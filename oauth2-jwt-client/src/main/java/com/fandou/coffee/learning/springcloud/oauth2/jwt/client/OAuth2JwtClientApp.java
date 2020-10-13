package com.fandou.coffee.learning.springcloud.oauth2.jwt.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
public class OAuth2JwtClientApp {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2JwtClientApp.class,args);
    }
}
