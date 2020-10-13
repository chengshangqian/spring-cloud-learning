package com.fandou.coffee.learning.springcloud.microservice.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loadbalance")
public class LoadBalancerClientController {

    // Ribbon的负载均衡由LoadBalancerClient实现
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     * 验证Ribbon的负载均衡的由loadBalancerClient实现
     *
     * @return
     */
    @GetMapping
    public String loadbalance(){
        // 每次负载均衡器会根据均衡策略获取最合适的服务
        ServiceInstance serviceInstance = loadBalancerClient.choose("micro-service-provider");
        return serviceInstance.getUri().toString();
    }

    /**
     * 不从eureka服务注册中心获取服务列表，而是自定义和维护本地服务列表
     *
     * @return
     */
    @GetMapping("/localservers")
    public String servers(){
        // 获取本地自定义的服务列表，示例定义在配置文件application.yaml中
        ServiceInstance serviceInstance = loadBalancerClient.choose("stores");
        return serviceInstance.getUri().toString();
    }
}
