package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service.remote;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.JwtAccessToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "oauth2-jwt-provider",fallback = Oauth2ServiceMock.class)
public interface Oauth2Service {
    @PostMapping("/oauth/token")
    JwtAccessToken getAccessToken(@RequestHeader("Authorization") String authorization
            , @RequestParam("grant_type") String type
            , @RequestParam("username") String username
            , @RequestParam("password") String password);
}
