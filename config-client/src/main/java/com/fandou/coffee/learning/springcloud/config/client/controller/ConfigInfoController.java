package com.fandou.coffee.learning.springcloud.config.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigInfoController {

    // 当前服务的私有配置
    @Value("${version}")
    private String version;

    // 以下三个是所有服务的共享配置
    @Value("${project.config.datasource.url}")
    private String datasourceUrl;

    @Value("${project.config.redis.host}")
    private String redisHost;

    @Value("${project.config.eureka.defaultZone}")
    private String eurekaDefaultZone;

    @GetMapping
    public String config(){
        String config = String.format("{version:'%s',datasourceUrl:'%s',redisHost:'%s',eurekaDefaultZone:'%s'}",version,datasourceUrl,redisHost,eurekaDefaultZone);
        System.out.printf("config => %s\n",config);
        return config;
    }
}
