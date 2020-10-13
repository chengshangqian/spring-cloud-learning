package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@Configuration
public class OAuth2JwtClientConfig {
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * JWT的存储
     *
     * @return
     */
    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * JWT访问令牌转换器：通过公钥解密JWT
     *
     * @return
     */
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        Resource resource = new ClassPathResource("public.cert");
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
}
