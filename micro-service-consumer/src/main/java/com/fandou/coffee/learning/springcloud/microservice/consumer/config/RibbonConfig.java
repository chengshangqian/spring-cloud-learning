package com.fandou.coffee.learning.springcloud.microservice.consumer.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon负载均衡配置类
 */
@Configuration
public class RibbonConfig {
    // 使用随机策略
    @Bean
    IRule loadBalanceRule(){
        return new RandomRule();
    }
}
