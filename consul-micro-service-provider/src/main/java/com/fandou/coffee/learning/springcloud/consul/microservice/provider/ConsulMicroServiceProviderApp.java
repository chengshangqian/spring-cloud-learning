package com.fandou.coffee.learning.springcloud.consul.microservice.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 开启Consul服务发现
public class ConsulMicroServiceProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsulMicroServiceProviderApp.class,args);
    }
}
