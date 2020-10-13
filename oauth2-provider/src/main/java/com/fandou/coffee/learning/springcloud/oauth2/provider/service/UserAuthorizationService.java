package com.fandou.coffee.learning.springcloud.oauth2.provider.service;

import com.fandou.coffee.learning.springcloud.oauth2.provider.dao.UserAuthorizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户认证服务，声明注册到容器后，security或oauth2在认证时会自动装配使用
 */
@Service
public class UserAuthorizationService implements UserDetailsService {
    @Autowired
    private UserAuthorizationDao userAuthorizationDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthorizationDao.selectUser(username);
    }
}
