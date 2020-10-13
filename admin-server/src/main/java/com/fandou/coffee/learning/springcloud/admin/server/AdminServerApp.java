package com.fandou.coffee.learning.springcloud.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient  // 与@EnableEurekaClient一样
public class AdminServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApp.class,args);
    }
}
