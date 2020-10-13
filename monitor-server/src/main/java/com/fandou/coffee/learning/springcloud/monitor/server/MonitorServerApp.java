package com.fandou.coffee.learning.springcloud.monitor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableEurekaClient // 开启Eureka的服务注册和发现
@EnableHystrixDashboard // 开启Hystrix的监控板
@EnableTurbine // 开启Turbine聚合监控面板
public class MonitorServerApp {
    public static void main(String[] args) {
        SpringApplication.run(MonitorServerApp.class,args);
    }
}
