package com.fandou.coffee.learning.springcloud.zuul.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy // 开启路由网关代理Zuul
public class ZuulServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApp.class,args);
    }
}
