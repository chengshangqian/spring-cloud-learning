package com.fandou.coffee.learning.springcloud.gateway.server.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义过滤器工厂：请求时间
 *
 */
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    private static final String KEY = "withParams";

    public RequestTimeGatewayFilterFactory(){
        super(Config.class);
    }

    /**
     * 创建过滤器
     *
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 放入请求开始时间
            exchange.getAttributes().put(REQUEST_TIME_BEGIN,System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                        // 如果请求种存在开始时间，则打印请求耗时
                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                        if(null != startTime){
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath());
                            sb.append(" : ")
                                    .append(System.currentTimeMillis() - startTime)
                                    .append("ms.");

                            // 如果设置了开启参数打印，则打印参数
                            if(config.isWithParams()){
                                sb.append(" Params : ").append(exchange.getRequest().getQueryParams());
                            }

                            System.out.println(sb.toString());
                        }
                    })
            );
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    /**
     * 是否同时打印参数
     */
    public static class Config {
        private boolean withParams;

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }
}
