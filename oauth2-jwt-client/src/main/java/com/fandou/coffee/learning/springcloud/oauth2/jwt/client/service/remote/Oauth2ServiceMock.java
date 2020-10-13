package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service.remote;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.JwtAccessToken;
import org.springframework.stereotype.Service;

@Service
public class Oauth2ServiceMock implements Oauth2Service {
    @Override
    public JwtAccessToken getAccessToken(String authorization, String type, String username, String password) {
        return null;
    }
}
