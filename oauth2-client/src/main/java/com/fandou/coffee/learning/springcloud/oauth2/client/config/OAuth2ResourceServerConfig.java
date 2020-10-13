package com.fandou.coffee.learning.springcloud.oauth2.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置：被保护的资源
 */
@Configuration
@EnableResourceServer // 开启资源服务器
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启全局方法安全认证配置
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 对象HttpSecurity进行设置：配置认证授权规则、常见的安全漏洞等
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 认证授权规则
                .authorizeRequests(authorizeRequestsCustomizer -> authorizeRequestsCustomizer
                        // 原样打印并返回消息不需要认证
                        .antMatchers(HttpMethod.GET,"/echos/{message}").permitAll()
                        // 其它请求需要认证
                        .anyRequest().authenticated()
                )

                // 关闭CSRF
                .csrf(csrfCustomizer -> csrfCustomizer.disable());
    }
}
