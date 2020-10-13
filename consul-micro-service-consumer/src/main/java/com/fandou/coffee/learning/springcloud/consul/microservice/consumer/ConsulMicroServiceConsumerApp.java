package com.fandou.coffee.learning.springcloud.consul.microservice.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsulMicroServiceConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsulMicroServiceConsumerApp.class,args);
    }
}
