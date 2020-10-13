package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.User;

import java.util.List;

public interface UserService {

    User get(Integer id);

    User getByUsername(String username);

    List<User> list();

    int create(User user);

    int update(User user);

    int updateUsername(User user);

    int updatePassword(User user);

    int delete(Integer id);
}
