package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.UserDTO;

public interface UserAuthorizationService {
    UserDTO authorize(String username, String password);
}
