package com.fandou.coffee.learning.springcloud.consul.microservice.consumer.service;

import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "consul-provider")
public interface RemoteUserService {

    @GetMapping("/user/{id}")
    User get(@PathVariable("id") String id);

    @GetMapping("/user/list")
    List<User> list();
}
