package com.fandou.coffee.learning.springcloud.admin.client;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AdminClientApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminClientApp.class,args);
    }
}
