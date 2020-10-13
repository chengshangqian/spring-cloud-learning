package com.fandou.coffee.learning.springcloud.gateway.server.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求处理时间打印过滤器：需要编写Java配置类将此过滤器进行注册,定义过滤器工厂会比较方便
 */
public class RequestTimeFilter implements GatewayFilter, Ordered {
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    /**
     * 过滤逻辑
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 放入请求开始时间
        exchange.getAttributes().put(REQUEST_TIME_BEGIN,System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    // 如果请求种存在开始时间，则打印请求耗时
                    Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if(null != startTime){
                        System.out.println(exchange.getRequest().getURI().getRawPath() + " : " + (System.currentTimeMillis() - startTime) + "ms.");
                    }
                })
        );
    }

    /**
     * 过滤器在所有同类过滤器中的执行顺序，越小优先级越大
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
