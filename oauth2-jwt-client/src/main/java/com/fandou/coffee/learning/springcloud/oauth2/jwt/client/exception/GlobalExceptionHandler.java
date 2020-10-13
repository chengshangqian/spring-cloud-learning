package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAuthorizationException.class)
    public ResponseEntity<String> handleException(Exception e) {

        return new ResponseEntity(e.getMessage(), HttpStatus.OK);
    }
}
