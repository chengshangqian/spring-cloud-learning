package com.fandou.coffee.learning.springcloud.microservice.consumer;

import com.fandou.coffee.learning.springcloud.microservice.consumer.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient // 开启Eureka的服务注册和发现
@RibbonClients(value = {
    // 定义服务hello-provider使用随机负载均衡策略，如果不定义，默认使用轮询策略
    @RibbonClient(name = "micro-service-provider",configuration = RibbonConfig.class)
})
@EnableFeignClients // 开启OpenFeign声明式远程调用
@EnableHystrix // 开启Hystrix熔断器
@EnableHystrixDashboard // 开启Hystrix监控面板
public class MicroServiceConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceConsumerApp.class,args);
    }
}
