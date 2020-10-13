package com.fandou.coffee.learning.springcloud.consul.microservice.provider.controller;

import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;
import com.fandou.coffee.learning.springcloud.consul.microservice.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
public class UserController {
    @Autowired
    private UserService userService;

    // 分区消费者调用的是哪个提供者节点的服务：测试用,也用于测试从配置中心读取数据
    @Value("${server.port}")
    private String port;

    // 使用配置中心动态刷新属性
    @Value("${cat.name}")
    private String catName;

    @GetMapping("/user/{id}")
    public User get(@PathVariable("id") String id){
        User user = userService.get(id);
        user.setPort(port);
        System.out.println(" cat name => " + catName);
        return user;
    }

    @GetMapping("/user/list")
    public List<User> list(){
        List<User> result = userService.list();
        for(User user : result){
            user.setPort(port);
        }
        return result;
    }
}
