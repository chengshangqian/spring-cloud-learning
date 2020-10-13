package com.fandou.coffee.learning.springcloud.admin.client.dao;

import com.fandou.coffee.learning.springcloud.admin.client.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {

    @Select("SELECT r.* FROM role r,user_role ur WHERE r.id = ur.role_id AND ur.user_id = #{userId}")
    List<Role> listByUserId(Integer userId);

    @Select("SELECT r.* FROM role r,role_api ra WHERE r.id = ra.role_id AND ra.api_id = #{apiId}")
    List<Role> listByApiId(Integer apiId);

    @Select("SELECT id,name,code FROM role WHERE id = #{id}")
    Role get(Integer id);

    @Select("SELECT id,name,code FROM role")
    List<Role> list();

    @Insert("INSERT INTO role (name,code) VALUES (#{name},#{code})")
    int create(Role role);

    @Update("UPDATE role SET name = #{name},code = #{code} WHERE id = #{id}")
    int update(Role role);

    @Delete("DELETE FROM role WHERE id = #{id}")
    int delete(Integer id);
}
