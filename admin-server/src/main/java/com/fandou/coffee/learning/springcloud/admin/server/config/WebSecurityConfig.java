package com.fandou.coffee.learning.springcloud.admin.server.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * 安全配置类：继承WebSecurityConfigurerAdapter，覆盖需要自定义配置的相关方法即可
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // 上下文
    private final String adminContextPath;

    // 通过构造器获取上下文
    public WebSecurityConfig(AdminServerProperties adminServerProperties){
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    /**
     * 配置http请求安全认证规则
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 认证成功处理器
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();

        // 跳转回到登录前的界面
        handler.setTargetUrlParameter("redirectTo");

        http
                /** 1、配置认证规则 **/
                .authorizeRequests(requestConfigurer -> requestConfigurer
                        // 静态页面和登录页面不需要认证
                        .antMatchers(adminContextPath+"/assets/**").permitAll()
                        .antMatchers(adminContextPath+"/login/**").permitAll()

                        // 剩下其它请求都需要认证，包括健康检查
                        .anyRequest().authenticated()
                )

                /** 2.配置登录、登出信息 **/
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage(adminContextPath+"/login")
                        .successHandler(handler)
                )
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl(adminContextPath+"/logout")
                )

                /** 3、配置csrf安全漏洞：关闭 **/
                .csrf(csrfConfigurer -> csrfConfigurer.disable())

                /** 4、设置认证方式 **/
                .httpBasic();
    }
}
