package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.controller;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.UserDTO;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证登录
 */
@RestController
@RequestMapping("/authorize")
public class UserAuthorizationController {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    /**
     * 认证登录
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    @PostMapping
    public UserDTO authorize(@RequestParam("username") String username, @RequestParam("password") String password){
        return userAuthorizationService.authorize(username,password);
    }
}
