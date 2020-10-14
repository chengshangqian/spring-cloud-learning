package com.fandou.coffee.learning.springcloud.microservice.provider.controller;

import com.fandou.coffee.learning.springcloud.microservice.common.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    // 问候服务
    @Autowired
    private GreetingService greetingService;

    // 打印端口，集群时判断负载均衡
    @Value("${server.port}")
    private String port;

    /**
     * 向来访的用户打招呼
     *
     * @param visitor
     * @return
     */
    @GetMapping("/{visitor}")
    public String greeting(@PathVariable("visitor") String visitor) {
        
        String result = String.format("server[%s] => %s", port, greetingService.greeting(visitor));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(result);
        }

        return result;
    }
}
