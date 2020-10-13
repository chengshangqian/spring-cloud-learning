package com.fandou.coffee.learning.springcloud.admin.client.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 开放API
 */
@Data
public class Api {
    // api id
    private int id;

    // api名称
    private String name;

    // api路径
    private String path;

    // api路径匹配的ant模式：api作为权限资源，使用pathAntPattern作为权限标识来实现认证授权
    private String pathAntPattern;

    // 角色列表：标识API被分配给了哪些角色或归属哪些角色，角色作为管理和分配权限资源的载体
    private Collection<? extends GrantedAuthority> roles;
}
