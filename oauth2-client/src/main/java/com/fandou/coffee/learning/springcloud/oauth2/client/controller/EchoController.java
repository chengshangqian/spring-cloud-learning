package com.fandou.coffee.learning.springcloud.oauth2.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * echo服务，用于测试OAuth2认证授权服务
 */
@RestController
@RequestMapping("/echos")
public class EchoController {

    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

    /**
     * 原样打印并返回消息：不需要授权即可访问
     *
     * @param message 消息
     * @return 原样返回消息
     */
    @GetMapping("/{message}")
    public String echo(@PathVariable("message") String message){

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("收到消息：{}",message);
        }

        return message;
    }

    /**
     * 打印消息并包装一层后返回：需要认证授权方可访问
     *
     * @param message 消息
     * @return 原样包装后的消息
     */
    @GetMapping("/wrap")
    public String wrap(@RequestParam("message") String message){

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("收到消息：{}",message);
        }

        return message;
    }

    /**
     * 获取认证信息：需要认证授权方可访问
     *
     * @param oAuth2Authentication  OAuth2认证信息，由框架注入
     * @param principal 认证主体信息，由框架注入
     * @param authentication 认证信息，由框架注入
     * @return 返回OAuth2认证信息
     */
    @GetMapping("/principal")
    public OAuth2Authentication getPrincipal(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication){

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("oAuth2Authentication => {}",oAuth2Authentication);
            LOGGER.debug("principal => {}",principal);
            LOGGER.debug("authentication => {}",authentication);
        }

        return oAuth2Authentication;
    }

    /**
     * 获取当前认证用户的名字：需要ADMIN角色授权方可访问
     *
     * @param principal 认证主体，由框架注入
     * @return 返回当前认证用户的名字
     */
    @GetMapping("/name")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // 增加角色限制
    public String getName(Principal principal){

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("用户名字 => {}",principal.getName());
        }

        return principal.getName();
    }
}
