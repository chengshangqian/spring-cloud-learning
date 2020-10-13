package com.fandou.coffee.learning.springcloud.admin.client.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationCaptchaException extends AuthenticationException {
    public VerificationCaptchaException(){
        super("验证码校验失败...");
    }
}
