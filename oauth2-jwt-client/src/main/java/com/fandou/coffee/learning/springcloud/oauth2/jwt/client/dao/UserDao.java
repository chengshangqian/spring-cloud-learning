package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.dao;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.Role;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("SELECT id,name,phone,username,password FROM user WHERE username = #{username}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="id",property="roles",many=@Many(select="selectRoles"))
    })
    User getByUsername(String username);

    @Select("SELECT id,name,phone,username,password FROM user WHERE id = #{id}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="id",property="roles",many=@Many(select="selectRoles"))
    })
    User get(Integer id);

    @Select("SELECT id,name,phone,username,password FROM user")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="id",property="roles",many=@Many(select="selectRoles"))
    })
    List<User> list();

    @Select("SELECT r.* FROM role r,user_role ur WHERE r.id = ur.role_id AND ur.user_id = #{id}")
    List<Role> selectRoles(Integer id);

    @Insert("INSERT INTO user (name,phone,username,password) VALUES (#{name},#{phone},#{username},#{password})")
    int create(User user);

    @Update("UPDATE user SET name = #{name},phone = #{phone} WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE user SET username = #{username} WHERE id = #{id}")
    int updateUsername(User user);

    @Update("UPDATE user SET password = #{password} WHERE id = #{id}")
    int updatePassword(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int delete(Integer id);
}
