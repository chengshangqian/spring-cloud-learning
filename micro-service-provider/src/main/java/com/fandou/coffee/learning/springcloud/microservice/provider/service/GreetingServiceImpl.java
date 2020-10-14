package com.fandou.coffee.learning.springcloud.microservice.provider.service;

import com.fandou.coffee.learning.springcloud.microservice.common.service.GreetingService;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String greeting(String visitor) {
        return String.format("Hello, %s! Welcome to SpringCloud!",visitor) ;
    }
}
