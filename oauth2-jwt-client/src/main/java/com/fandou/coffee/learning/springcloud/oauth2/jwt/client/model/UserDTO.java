package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model;

import lombok.Data;

@Data
public class UserDTO {
    User user;
    JwtAccessToken jwtAccessToken;
}
