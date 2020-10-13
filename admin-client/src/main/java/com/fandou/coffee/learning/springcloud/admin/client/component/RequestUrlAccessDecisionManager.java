package com.fandou.coffee.learning.springcloud.admin.client.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 请求资源访问决策管理器：决定当前用户是否能访问所请求的URL对应的资源
 */
public class RequestUrlAccessDecisionManager implements AccessDecisionManager {

    /**
     * 决策
     *
     * @param authentication 认证用户，含有权限（角色）等信息
     * @param filterInvocation FilterInvocation
     * @param requestSecurityConfigAttributes 当前请求所需的权限（角色）
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object filterInvocation, Collection<ConfigAttribute> requestSecurityConfigAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当前认证用户的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 遍历当前的请求所需要的权限，然后与用户具有的权限进行比较
        for (ConfigAttribute requestSecurityConfigAttribute: requestSecurityConfigAttributes) {
            String requiredRole = requestSecurityConfigAttribute.getAttribute();
            // 如果上一步骤最后返回的需要登录即可访问的权限（没有限定角色）
            if(RequestUrlSecurityAttributes.DEFAULT_ROLE_REQUIRED.equals(requiredRole)){
                // 如果未登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new BadCredentialsException("未登录");
                }
                // authentication如果是UsernamePasswordAuthenticationToken或RememberMeAuthenticationToken子类对象，说明已经登录
                // else if(authentication instanceof UsernamePasswordAuthenticationToken)
                return;
            }

            // 遍历比较用户具有的权限，与当前请求的权限之一进行比较
            for (GrantedAuthority authority : authorities ) {
                if(requiredRole.equals(authority.getAuthority())){
                    // 有匹配即返回
                    return;
                }
            }
        }

        // 抛出异常
        throw new AccessDeniedException("权限不足.");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
