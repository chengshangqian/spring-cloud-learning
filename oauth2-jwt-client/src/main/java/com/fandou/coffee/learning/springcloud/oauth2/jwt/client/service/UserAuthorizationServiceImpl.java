package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.exception.UserAuthorizationException;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.JwtAccessToken;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.User;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.UserDTO;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service.remote.Oauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Base64;

@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Oauth2Service oauth2Service;

    // 在授权服务端声明的当前客户端的id和密码
    @Value("${project.security.oauth2.client.client-id}")
    private String clientId;

    @Value("${project.security.oauth2.client.client-secret}")
    private String clientSecret;

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public UserDTO authorize(String username, String password) {
        // 查询用户
        User user = userService.getByUsername(username);

        if (null == user) {
            throw new UserAuthorizationException("用户[" + username + "]不存在...");
        }

        // 校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserAuthorizationException("用户密码错误...");
        }

        // 在授权服务端注册声明的当前客户端的id和密码
        String clientDetails = String.format("%s:%s",clientId,clientSecret);
        String clientAccessToken = Base64.getEncoder().encodeToString(clientDetails.getBytes(Charset.forName("UTF-8")));
        String authorization = String.format("Basic %s",clientAccessToken); // 安全认证请求头信息，token类型为Basic，即HttpBasic认证

        String grantType = "password"; // 认证登录使用password类型

        // jwt格式的访问令牌
        JwtAccessToken jwtAccessToken = oauth2Service.getAccessToken(authorization, grantType, username, password);

        if (jwtAccessToken == null) {
            throw new UserAuthorizationException("授权服务器异常...");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setJwtAccessToken(jwtAccessToken);
        userDTO.setUser(user);

        return userDTO;
    }
}
