package com.fandou.coffee.learning.springcloud.oauth2.provider.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * OAuth2授权服务提供端配置，即授权服务器配置
 */
@Configuration
@EnableResourceServer // 开启资源服务
@EnableAuthorizationServer // 开启授权服务
public class OAuth2ProviderConfig extends AuthorizationServerConfigurerAdapter {
    // 数据源
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    // 认证管理器
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    // 认证用户详情查询服务
//    @Autowired
//    private UserAuthorizationService userAuthorizationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // jdbc令牌存储
    @Bean
    JdbcTokenStore jdbcTokenStore(){
       return new JdbcTokenStore(dataSource);
    }

    /**
     * 授权服务器安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);
                //.passwordEncoder(NoOpPasswordEncoder.getInstance()); // 如果客户端的secret配置为明文时使用此密码编码器
    }

    /**
     * 配置受安全保护的客户端信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO 实现从数据库获取配置的客户端信息
        // clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
        clients.inMemory()
                // 配置浏览器客户端
                .withClient("browser") // 客户端id
                .authorizedGrantTypes("refresh_token","password") // 客户端权限：刷新访问令牌、使用密码认证登录（申请访问令牌）
                .scopes("ui") // 约定作用域，用于前端

                // 配置oauth2-client微服务客户端
                .and()
                .withClient("oauth2-client") // 客户端id
                .secret("$2a$12$3njFpV27MDo9ervf95ukiOJE7CG9Cm/25Qdj7DLqV6bKAPxP4k3pe") // 客户端认证密码，123456
                .authorizedGrantTypes("client_credentials","refresh_token","password") // 客户端权限：客户端凭证管理、刷新访问令牌、使用密码认证登录（申请访问令牌）
                .scopes("server"); // 约定作用域，用于后台服务
    }

    /**
     * 配置访问令牌的存储方式和用户身份认证方式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jdbcTokenStore()) // 令牌存储到数据库：oauth_access_token和oauth_refresh_token表
                .authenticationManager(authenticationManager); // 认证管理器
                //.userDetailsService(userAuthorizationService); // 身份信息查询服务,从数据库查询用户信息，配置了认证管理器，userDetailsService可以不需要设置
    }
}
