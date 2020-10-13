package com.fandou.coffee.learning.springcloud.microservice.provider.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 服务提供调用服务消费者服务简单示例：即互为服务提供者和消费者
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用消费方提供的服务接口，即eureka的client端可以是服务提供方的同时也是服务消费方，反之亦然
     *
     * @return
     */
    @GetMapping("/{service}/{path}")
    @HystrixCommand(fallbackMethod = "invokeConsumerServiceMock")
    public String invokeConsumerService(@PathVariable("service") String service,@PathVariable(value = "path") String path,@RequestParam(value = "protocol",required = false) String protocol){

        if(protocol == null){
            protocol = "http";
        }

        assert null != service;
        assert null != path;

        if(path.startsWith("/")){
            path = path.substring(1);
        }

        String url = String.format("%s://%s/%s",protocol,service,path);

        return restTemplate.getForObject(url,String.class);
    }

    /**
     * 熔断处理
     *
     * @param service
     * @param path
     * @param protocol
     * @return
     */
    public String invokeConsumerServiceMock(String service, String path, String protocol){
        System.out.printf("{protocol -> %s, service -> %s, path -> %s}\n" , protocol,service,path);
        return "服务器繁忙，请稍后再试.";
    }
}
