package com.fandou.coffee.learning.springcloud.oauth2.client.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * OAuth2客户端配置
 */
@Configuration
@EnableOAuth2Client // 开启OAuth2 Client
@EnableConfigurationProperties
public class OAuth2ClientConfig {
    /**
     * 受保护资源在OAuth2 Provider声明的信息和验证URI等信息
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails(){
        return new ClientCredentialsResourceDetails();
    }

    /**
     * 请求拦截器
     *
     * 当第三方访问本地受保护的资源时，OAuth2客户端将获取请求携带的访问令牌和令牌类型信息一起，带上客户端相关认证凭据，
     * 向授权服务器发起远程验证，验证通过方可访问被保护的资源
     *
     * @return
     */
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(),clientCredentialsResourceDetails());
    }

    /**
     * 发起验证请求的RestTemplate
     *
     * @return
     */
    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate(){
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }
}
