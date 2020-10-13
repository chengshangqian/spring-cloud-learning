package com.fandou.coffee.learning.springcloud.oauth2.jwt.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OAuth2JwtProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2JwtProviderApp.class,args);
    }
}
