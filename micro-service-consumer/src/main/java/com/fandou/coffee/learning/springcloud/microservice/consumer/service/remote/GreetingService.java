package com.fandou.coffee.learning.springcloud.microservice.consumer.service.remote;

import com.fandou.coffee.learning.springcloud.microservice.consumer.config.OpenFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 本地问候服务接口
 *
 * 使用@FeignClient注解声明此接口使用OpenFeign调用远程服务
 *
 */
@FeignClient(
        value = "micro-service-provider" // 远程服务，即要调用的远程服务名
        , configuration = OpenFeignConfig.class // 远程调用配置类，定义远程调用的相关配置，如重试策略等
        , fallback = GreetingServiceMock.class // 服务降级所调用的Mock类，熔断器打开时，将调用Mock类对应的接口
)
public interface GreetingService {

    /**
     * 声明一个本地greeting接口
     * 接口的元数据信息与远程服务接口一致，方法名可以不一样
     *
     * @param visitor 访客昵称
     * @return 问候语
     */
    @GetMapping("/greeting/{visitor}")
    String greeting(@PathVariable("visitor") String visitor);
}
