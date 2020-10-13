package com.fandou.coffee.learning.springcloud.admin.client.config;

import com.fandou.coffee.learning.springcloud.admin.client.component.RequestUrlAccessDecisionManager;
import com.fandou.coffee.learning.springcloud.admin.client.component.RequestUrlSecurityAttributes;
import com.fandou.coffee.learning.springcloud.admin.client.filter.VerificationCaptchaFilter;
import com.fandou.coffee.learning.springcloud.admin.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * Web安全配置类
 * 配置资源访问或请求访问规则：包括认证信息、认证规则（忽略、基于角色或权限进行授权、登入登出、常见安全漏洞设置等）
 */
@Configuration
//@EnableRedisHttpSession // 使用redis保存会话
//@EnableWebSecurity // 引导类已经注解@SpringBootApplication，不需要额外注解@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 忽略的URL模式：直接跳过认证授权，同时不支持/不响应GET之外的请求
    private final String[] ignoringAntPatterns = new String[]{
            "/favicon.ico",  // 浏览器标签图标
            "/css/**", "/js/**","/images/**","/img/**",  // 静态资源
            "/actuator/**",  // 应用监控
            "/swagger**/**","/v3/**",  // 在线API文档
            "/captcha.jpg",  // 验证码
            "/","/index.htm","/index.html","/index"  // 首页
    };

    // 许可的URL模式：即不需要认证的URL，进入认证授权的校验流程，在动态权限配置过滤器中用于排除比较
    private final String[] permitAntPatterns = new String[]{
            "/api/**",  // 开放api
            "/login","/login-error"  // 登录相关
    };

    /**
     * 对WebSecurity进行配置：比如忽略静态资源、系统监控、API文档等
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略认证授权的请求URL模式：
        // 被忽略的请求，只能使用GET访问，如果需要响应POST等GET之外的请求，请在permitAntPatterns上配置
        web.ignoring().antMatchers(ignoringAntPatterns);
    }

    /**
     * 对象HttpSecurity进行设置：配置认证授权规则、常见的安全漏洞等
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 1、设置认证授权规则方式一：使用拦截器，利用ObjectPostProcessor机制和两个接口从数据库或缓存中加载认证授权规则
                // 缺省所有请求开放访问，具体的认证授权规则由接下来的拦截器去实现
                // 使用拦截器需要有一次permitAll()、denyAll()、anonymous()、authenticated()、fullyAuthenticated()、rememberMe()的设置，不知道原因，
                // 否则启动时在创建springSecurityFilterChain这个bean时会出现异常
                // java.lang.IllegalStateException: At least one mapping is required (i.e. authorizeRequests().anyRequest().authenticated())
                .authorizeRequests((authorizeRequestsCustomizer) -> authorizeRequestsCustomizer
                        // 缺省所有请求开放访问，具体的认证授权规则由接下来的拦截器去实现
                        .anyRequest().permitAll()

                        // 添加一个安全拦截器，过滤每次请求（ignoring或permitAll属性变量配置的除外）：
                        // 所有认证授权规则由应用后台配置，从数据库或缓存中加载，不需要硬编码，实现动态配置权限
                        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                                // 需要忽略登录相关接口
                                object.setSecurityMetadataSource(requestUrlSecurityAttributes());
                                object.setAccessDecisionManager(requestUrlAccessDecisionManager());
                                return object;
                            }
                        })
                )

                // 1、设置认证授权规则方式二：使用ANT模式，硬编码配置权限
                /*.authorizeRequests((authorize) -> authorize
                        .antMatchers("/user/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN") // 具有用户或管理员角色授权
                        .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // 具有管理员角色授权
                )*/

                // 设置登录
                .formLogin((formLoginCustomizer) -> formLoginCustomizer
                        .loginPage("/login")
                        .failureUrl("/login-error")
                )

                // 开启 记住我 功能
                .rememberMe(rememberMeCustomizer -> rememberMeCustomizer
                        .key("rmb-me") // cookie一部分
                        .rememberMeCookieName("rmb-me") // cookie名称
                        .tokenValiditySeconds(60 * 60 * (8 + 2)) // 设置10个小时：发现部分google浏览器的cookie时间比本地时间晚8个小时，直接就过期无效
                        .userDetailsService(userService) // 设置重新登录时查询用户信息所用的userDetailsService
                )

                // 设置登出
                .logout((logoutCustomizer) -> logoutCustomizer
                        .logoutUrl("/logout") // 设置登出请求URL
                        .clearAuthentication(true) // 清除认证信息
                        .invalidateHttpSession(true) // 会话失效
                        .deleteCookies("user-token") // 清除cookie凭证
                        .logoutSuccessUrl("/") // 正常登出成功跳转URL，如果不设置默认跳回登录页，此时登录会多一次登录跳转且需要重新登录
                )

                // (并发)会话设置
                .sessionManagement(sessionManagementConfigurer ->  sessionManagementConfigurer
                        // 单个用户允许同时在线的最大会话数：现在浏览器一台机器会当作一个会话jsessionId相同
                        .maximumSessions(1)
                        // 超过maximumSessions设置的会话数时，是否阻止新会话创建，true表示阻止；默认为false，表示创建新会话，同时旧会话失效。
                        // 如果设置为true，需要注册一个HttpSessionEventPublisher来实现会话注销，否则会话满了，即使退出，页无法在登录，同时自定义的UserDetails应覆盖hashCode和equals方法
                        .maxSessionsPreventsLogin(false)
                        // 会话失效跳转URL：单个用户旧会话失效后，继续访问会报异常This session has been expired...，此时可以设置expiredUrl失效后跳转页面
                        .expiredUrl("/login") // 可以设置一个提示页面：您的账号在其它地方登录...
                )

                // 跨域设置
                .cors(corsConfigurer -> {
                    corsConfigurer.configurationSource(corsConfigurationSource);
                })

                // 关闭CSRF
                .csrf(csrfCustomizer -> csrfCustomizer.disable())

                // 验证码验证过滤器
                .addFilterBefore(verificationCaptchaFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 用户服务：用于自动登录时根据cookie信息查询用户信息
     */
    @Autowired
    private UserService userService;

    /**
     * 跨域配置
     */
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    /**
     * 验证码过滤器
     */
    @Autowired
    private VerificationCaptchaFilter verificationCaptchaFilter;

    /**
     * 密码编码器：认证时，用于对输入的密码进行编码，然后和数据库中的密码进行比较
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 长度16会很影响性能，可以使用默认10
        return new BCryptPasswordEncoder(12);
    }

    // 绑定在请求上的授权配置：即访问某个请求所需要权限
    @Bean
    FilterInvocationSecurityMetadataSource requestUrlSecurityAttributes(){
        // 忽略permitAll中的URL模式
        return new RequestUrlSecurityAttributes(permitAntPatterns);
    }

    /**
     * 请求访问决策管理器：根据当前用户的认证授权信息以及绑定在请求上的权限，决定当前用户是否可以访问当前的请求资源
     *
     * @return 请求访问决策管理器
     */
    @Bean
    AccessDecisionManager requestUrlAccessDecisionManager(){
        return new RequestUrlAccessDecisionManager();
    }

    /**
     * 注册会话事件发布器
     * 当并发会话管理配置了不允许覆盖旧的会话时maxSessionsPreventsLogin(true)，务必注册HttpSessionEventPublisher
     * 以保证正常退出时能正确清除会话，否则一旦达到单个用户最大会话数时，即时正常退出后，也会导致无法再登录。
     * 另外如果会话数不止1个，由于SpringSecurity会话管理，其会话注册使用了用户详情对象UserDetails（User）作为key，
     * 而且从数据库总查询用户都是创建新的UserDetails对象，key就会存在不一样，同时加上记住我等功能，对于并发控制可能会出现问题，
     * 为了并发环境确保正常，自定义的UserDetails对象还应该覆盖hashCode和equals方法
     * 集群会话，也应配置
     * @return
     */
    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }
}
