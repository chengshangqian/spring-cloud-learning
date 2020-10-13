package com.fandou.coffee.learning.springcloud.consul.microservice.provider.service;

import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;
import com.fandou.coffee.learning.springcloud.consul.microservice.common.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User get(String id) {
        User user = new User();
        user.setId(id);
        user.setName("Sam");
        return user;
    }

    @Override
    public List<User> list() {
        List<User> result = new ArrayList<>(2);
        User sam = new User();
        sam.setId("1");
        sam.setName("Sam");
        result.add(sam);

        User cheng = new User();
        cheng.setId("2");
        cheng.setName("C.Cheng");
        result.add(cheng);

        return result;
    }
}
