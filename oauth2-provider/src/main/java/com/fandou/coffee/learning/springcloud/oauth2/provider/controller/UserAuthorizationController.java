package com.fandou.coffee.learning.springcloud.oauth2.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/authorize")
public class UserAuthorizationController {
    /**
     * 获取当前认证通过主体信息,用于提供给资源服务器校验认证
     *
     * @param principal 当前认证通过主体信息，由框架注入
     * @return 当前认证通过主体信息
     */
    @GetMapping("/principal")
    public Principal getCurrentPrincipal(Principal principal){
        return principal;
    }
}
