package com.fandou.coffee.learning.springcloud.oauth2.jwt.provider.dao;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.provider.model.Role;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.provider.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 用户认证服务DAO
 */
@Mapper
public interface UserAuthorizationDao {

    @Select("SELECT id,name,phone,username,password FROM user WHERE username = #{username}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="id",property="roles",many=@Many(select="selectRoles"))
    })
    User selectUser(String username);

    @Select("SELECT r.* FROM role r,user_role ur WHERE r.id = ur.role_id AND ur.user_id = #{id}")
    List<Role> selectRoles(Integer id);
}
