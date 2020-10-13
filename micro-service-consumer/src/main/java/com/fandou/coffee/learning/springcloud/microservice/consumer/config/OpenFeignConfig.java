package com.fandou.coffee.learning.springcloud.microservice.consumer.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * OpenFeign远程调用配置类
 */
@Configuration
public class OpenFeignConfig {

    // OpenFeign远程调用重试策略
    @Bean
    public Retryer retryer(){
        return new Retryer.Default(100,SECONDS.toMillis(1),5);
    }
}
