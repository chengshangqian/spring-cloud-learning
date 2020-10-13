package com.fandou.coffee.learning.springcloud.admin.client.dao;

import com.fandou.coffee.learning.springcloud.admin.client.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("SELECT id,id userId,name,phone,username,password FROM user WHERE username = #{username}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="userId",property="roles",
                    many=@Many(
                            select="com.fandou.coffee.learning.springcloud.admin.client.dao.RoleDao.listByUserId"
                            //,fetchType= FetchType.LAZY
                    )
            )
    })
    User getByUsername(String username);

    @Select("SELECT id,id userId,name,phone,username,password FROM user WHERE id = #{id}")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="userId",property="roles",
                    many=@Many(
                            select="com.fandou.coffee.learning.springcloud.admin.client.dao.RoleDao.listByUserId"
                            //,fetchType= FetchType.LAZY
                    )
            )
    })
    User get(Integer id);

    @Select("SELECT id,id userId,name,phone,username,password FROM user")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="phone",property="phone"),
            @Result(column="username",property="username"),
            @Result(column="password",property="password"),
            @Result(column="userId",property="roles",
                    many=@Many(
                            select="com.fandou.coffee.learning.springcloud.admin.client.dao.RoleDao.listByUserId"
                            //,fetchType= FetchType.LAZY
                    )
            )
    })
    List<User> list();

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
