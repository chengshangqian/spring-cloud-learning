package com.fandou.coffee.learning.springcloud.oauth2.jwt.provider.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色：资源分类，实现授权接口
 */
@Data
public class Role implements GrantedAuthority{

    // 序列化id
    private static final long serialVersionUID = -1000000001L;

    // 角色id
    private int id;

    // 角色名称
    private String name;

    // 角色编码
    private String code;

    /**
     * 使用角色编码作为权限标识
     *
     * @return
     */
    @Override
    public String getAuthority() {
        return code;
    }
}
