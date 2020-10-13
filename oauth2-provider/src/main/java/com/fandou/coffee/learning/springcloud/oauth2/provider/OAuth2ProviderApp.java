package com.fandou.coffee.learning.springcloud.oauth2.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OAuth2ProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2ProviderApp.class,args);
    }
}
