package com.fandou.coffee.learning.springcloud.admin.client.component;

import com.fandou.coffee.learning.springcloud.admin.client.model.Api;
import com.fandou.coffee.learning.springcloud.admin.client.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 请求资源安全属性：请求访问的资源绑定的权限信息，比如角色
 */
public class RequestUrlSecurityAttributes implements FilterInvocationSecurityMetadataSource {

    // 缺省认证角色
    public static final String DEFAULT_ROLE_REQUIRED = "ROLE_LOGIN";

    // ant模式路径匹配器
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

   // 许可的URL模式
    private String[] permitAntPatterns;

    // 开放API服务
    @Autowired
    private ApiService apiService;

    public RequestUrlSecurityAttributes(String... permitAntPatterns){
        this.permitAntPatterns = permitAntPatterns;
    }

    /**
     * 获取当前请求URL的权限属性
     *
     * @param filterInvocation
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object filterInvocation) throws IllegalArgumentException {
        // 当前请求URL
        String url = ((FilterInvocation) filterInvocation).getRequestUrl();

        // 排除许可的URL模式：即不需要认证的URL
        if(null != permitAntPatterns){
            for (String antPattern : permitAntPatterns) {
                // 如果是续可的URL，直接返回null，可继续访问资源
                if(antPathMatcher.match(antPattern,url)){
                    return null;
                }
            }
        }

        // 权限资源列表：开放API或菜单或按钮等，都可以设计为权限资源
        List<Api> apiResources = apiService.list();

        // 遍历权限资源列表即开放api列表
        for (Api apiResource : apiResources ) {
            // 检查请求的URL是否属于指定授权的资源
            if(antPathMatcher.match(apiResource.getPathAntPattern(),url)){
                // 假设查询结果一共有两个角色拥有或被分配了此API的权限
                String[] roles = apiResource.getRoles().parallelStream().map(role -> role.getAuthority()).toArray(String[]::new);
                if(roles != null && roles.length > 0){
                    return SecurityConfig.createList(roles);
                }
            }
        }

        // 如果不属于权限资源，缺省为ROLE_LOGIN，即登录后访问：由接下来的请求资源访问决策管理器决定即RequestUrlAccessDecisionManager
        return SecurityConfig.createList(DEFAULT_ROLE_REQUIRED);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 类型校验
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
