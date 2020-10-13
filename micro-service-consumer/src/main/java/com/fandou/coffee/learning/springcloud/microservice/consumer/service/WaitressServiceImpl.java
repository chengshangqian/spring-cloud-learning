package com.fandou.coffee.learning.springcloud.microservice.consumer.service;

import com.fandou.coffee.learning.springcloud.microservice.consumer.service.remote.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 本地服务
 * 可以在Controller中使用@FeignClient注解的远程服务接口实现，但很多时候，
 * 都是在本地的Service层调用远程服务接口，以便与本地业务一起作事务控制
 */
@Service
@Primary
public class WaitressServiceImpl implements WaitressService {

    @Autowired
    private GreetingService greetingService; // openfeign远程服务接口

    @Override
    public String greeting(String visitor) {
        String greeting = greetingService.greeting(visitor);
        return String.format("%s What can i help you?", greeting);
    }
}
