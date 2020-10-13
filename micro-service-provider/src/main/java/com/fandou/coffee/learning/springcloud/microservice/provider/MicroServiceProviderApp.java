package com.fandou.coffee.learning.springcloud.microservice.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix // 开启Hystrix熔断器
@EnableHystrixDashboard // 开启Hystrix监控面板
public class MicroServiceProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceProviderApp.class,args);
    }
}
