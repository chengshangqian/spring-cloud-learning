package com.fandou.coffee.learning.springcloud.microservice.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 本地服务
 * 可以在Controller中使用@FeignClient注解的远程服务接口实现，但很多时候，
 * 都是在本地的Service层调用远程服务接口，以便与本地业务一起作事务控制
 */
@Service("lbrestWaitressService")
public class LoadbalanceRestWaitressServiceImpl extends AbstractWaitressService {

    // 服务名称或消费提供方：硬编码仅作测试
    private final String serviceId = "micro-service-provider";

    // bean定义在ConsumerConfiguration文件中
    // 标注了@LoadBalanced注解，以使用Ribbon做负载均衡功能
    @Autowired
    @Qualifier("lbRestTemplate")
    private RestTemplate lbRestTemplate;

    /**
     * 使用负载均衡的restTemplate调用远程服务
     * 此时使用了Eureka发现远程服务，然后由Ribbon实现负载均衡，再由restTemplate调用远程服务
     * 如果Ribbon没有定义负载均衡策略将使用轮询策略，当前定义了随机策略，见RibbonRandomRuleConfig配置
     *
     * @param visitor
     * @return
     */
    @Override
    @HystrixCommand(fallbackMethod = "greetingMock") // 注解使用Hystrix熔断器，出现异常，使用helloMockHandler方法处理
    public String doGreeting(String visitor){
        // Eureka会根据Ribbon中默认或自定义的负载均衡策略解析为正确的服务提供者
        // 由于使用了Eureka发现服务，此时不需要在定义为具体的远程服务主机地址，而是使用服务名称serviceId进行调用
        String url = "http://" + serviceId + "/greeting/" + visitor;
        return lbRestTemplate.getForObject(url,String.class);
    }

    /**
     * 异常处理：作熔断，即降级，统一都返回此问候提示，类似Mock机制
     *
     * @return
     */
    public String greetingMock(String visitor){
        System.out.printf("visitor => %s\n" , visitor);
        // 返回友好提示
        return "Hello," + visitor + "! The Application is busy now, you can try again later! ";
    }
}
