package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.exception;

/**
 * 登录异常
 */
public class UserAuthorizationException extends RuntimeException {
    public UserAuthorizationException(String message){
        super(message);
    }
}
