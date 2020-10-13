package com.fandou.coffee.learning.springcloud.microservice.consumer.service;

/**
 * 服务员接口实现类
 *
 * 可以在Controller中使用@FeignClient注解的远程服务接口，但很多时候，
 * 都是在本地的Service层调用远程服务接口，以便与本地业务一起作事务控制
 */
public abstract class AbstractWaitressService implements WaitressService {

    /**
     * 包装为新的问候语
     *
     * @param visitor 访客昵称
     * @return 问候语
     */
    @Override
    public String greeting(String visitor) {
        /**
         *  eureka会根据ribbon中默认或自定义的负载均衡策略解析为正确的服务提供者，然后由openfeign进行远程调用。
         *  远程服务接口的具体信息在GreetingService接口上注解声明。
         *  openfeign配合Mock类来实现服务降级，openfeign默认依赖了hystrix,在pom中可以不需要显示引入hystrix依赖
         */
        String greeting = doGreeting(visitor);

        return String.format("%s What can i help you?", greeting);
    }

    protected abstract String doGreeting(String visitor);
}
