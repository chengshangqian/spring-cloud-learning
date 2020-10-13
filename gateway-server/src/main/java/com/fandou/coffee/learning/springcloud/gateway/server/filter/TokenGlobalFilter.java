package com.fandou.coffee.learning.springcloud.gateway.server.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 定义全局过滤器
 */
public class TokenGlobalFilter implements GlobalFilter, Ordered {
    // 定义控制参数
    @Value("${filter.global.token.enabled:false}")
    private boolean globalTokenEnabled;
    @Value("${filter.global.token.name:token}")
    private String tokenName; // token名称，默认为token
    @Value("${filter.global.token.type:Bearer}")
    private String tokenType; // token类型
    @Value("${filter.global.token.source:query}")
    private String tokenSource; // token取值来源：query，cookie

    /**
     * 过滤逻辑
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if(globalTokenEnabled) {
            // 获取token
            String token = null;

            switch (tokenSource){
                case "query" :
                    token = exchange.getRequest().getQueryParams().getFirst(tokenName);
                    break;
                case "cookie" :
                    token = exchange.getRequest().getCookies().getFirst(tokenName).getValue();
                    break;
                default :
                    break;
            }

            System.out.println("token => " + token);

            // 检查token是否为空，如果为空，则返回
            if (null == token || token.isEmpty()) {
                System.out.println("error : token is required but empty...");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        // 如果没有开启检测或存在token，继续调用
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
