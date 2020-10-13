package com.fandou.coffee.learning.springcloud.microservice.consumer.service.remote;

import org.springframework.stereotype.Service;

/**
 * GreetingService的Mock类，用于服务降级
 */
@Service
public class GreetingServiceMock implements GreetingService {
    @Override
    public String greeting(String visitor) {
        return "Hello," + visitor + "! The application is busy now, you can try again later! Thank you!";
    }
}
