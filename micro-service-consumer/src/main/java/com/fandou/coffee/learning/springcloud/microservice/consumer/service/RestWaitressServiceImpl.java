package com.fandou.coffee.learning.springcloud.microservice.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 本地服务
 * 可以在Controller中使用@FeignClient注解的远程服务接口实现，但很多时候，
 * 都是在本地的Service层调用远程服务接口，以便与本地业务一起作事务控制
 */
@Service("restWaitressService")
public class RestWaitressServiceImpl extends AbstractWaitressService {

    // 服务名称或消费提供方：硬编码仅作测试
    private final String serviceHost = "localhost:8080";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 普通的restTemplate远程调用服务
     * 此时和服务注册中心Eureka无关，也没有负载均衡注解
     *
     * @param visitor
     * @return
     */
    @Override
    public String doGreeting(String visitor) {
        // 使用普通的restTemplate调用远程服务
        // 需要使用服务提供者的具体主机地址serviceHost，不涉及服务注册和发现，只是普通的http调用
        String url = "http://" + serviceHost + "/greeting/" + visitor;
        return restTemplate.getForObject(url,String.class);
    }
}
