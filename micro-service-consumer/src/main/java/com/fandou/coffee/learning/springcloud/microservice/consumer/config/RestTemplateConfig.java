package com.fandou.coffee.learning.springcloud.microservice.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate远程调用配置类
 */
@Configuration
public class RestTemplateConfig {
    // 普通的RestTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 使用了Ribbon负载均衡的RestTemplate
    @Bean("lbRestTemplate")
    @LoadBalanced
    public RestTemplate loadBalanceRestTemplate() {
        return new RestTemplate();
    }
}
