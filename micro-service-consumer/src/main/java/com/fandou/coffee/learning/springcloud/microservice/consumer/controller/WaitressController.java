package com.fandou.coffee.learning.springcloud.microservice.consumer.controller;

import com.fandou.coffee.learning.springcloud.microservice.consumer.service.WaitressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class WaitressController {

    @Autowired
    private WaitressService waitressService;

    /**
     * 向到访的访客问好
     *
     * @param visitor 访客昵称
     * @return 问候语
     */
    @GetMapping("/{visitor}")
    public String greeting(@PathVariable("visitor") String visitor){
        return waitressService.greeting(visitor);
    }
}
