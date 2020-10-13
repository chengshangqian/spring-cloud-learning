package com.fandou.coffee.learning.springcloud.microservice.consumer.controller;

import com.fandou.coffee.learning.springcloud.microservice.consumer.service.WaitressService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class MultiWaitressController implements ApplicationContextAware {
    // 应用上下文
    private ApplicationContext applicationContext;

    /**
     * 向到访的访客问好
     *
     * @param invokeType feign | lbrest | rest
     * @param visitor 访客昵称
     * @return 问候语
     */
    @GetMapping("/{invokeType}/{visitor}")
    public String greeting(@PathVariable("invokeType") String invokeType,@PathVariable("visitor") String visitor){
        return getWaitressService(invokeType).greeting(visitor);
    }

    private WaitressService getWaitressService(String beanNamePrefix){
        return applicationContext.getBean(String.format("%sWaitressService",beanNamePrefix),WaitressService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
