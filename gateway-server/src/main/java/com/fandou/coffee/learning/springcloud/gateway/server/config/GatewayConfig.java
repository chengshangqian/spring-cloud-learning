package com.fandou.coffee.learning.springcloud.gateway.server.config;

import com.fandou.coffee.learning.springcloud.gateway.server.filter.RequestTimeGatewayFilterFactory;
import com.fandou.coffee.learning.springcloud.gateway.server.filter.TokenGlobalFilter;
import com.fandou.coffee.learning.springcloud.gateway.server.resolver.HostAddrKeyResolver;
import com.fandou.coffee.learning.springcloud.gateway.server.resolver.UriKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayConfig {
    /**
     * 过滤器工厂Bean
     *
     * @return
     */
    @Bean
    @Profile({"directUri","discovery"})
    public RequestTimeGatewayFilterFactory requestTimeGatewayFilterFactory(){
        return new RequestTimeGatewayFilterFactory();
    }

    /**
     * 全局过滤器
     *
     * @return
     */
    @Bean
    @Profile("discovery") // 其中一个profile激活即可创建bean
    public TokenGlobalFilter tokenGlobalFilter(){
        return new TokenGlobalFilter();
    }

    /**
     * 服务限流
     *
     * @return
     */
    @Bean
    @Profile("discovery")
    @Primary
    public UriKeyResolver uriKeyResolver(){
        return new UriKeyResolver();
    }

    /**
     * 服务限流
     *
     * @return
     */
    @Bean
    @Profile("discovery")
    public HostAddrKeyResolver hostAddrKeyResolver (){
        return new HostAddrKeyResolver();
    }
}
