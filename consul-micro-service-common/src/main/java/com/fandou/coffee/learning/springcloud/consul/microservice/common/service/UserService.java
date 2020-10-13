package com.fandou.coffee.learning.springcloud.consul.microservice.common.service;

import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;

import java.util.List;

public interface UserService {
    User get(String id);

    default void put(User user) {

    }

    default void delete(User user) {
    }

    List<User> list();
}
