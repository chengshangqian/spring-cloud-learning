package com.fandou.coffee.learning.springcloud.admin.client.controller;

import com.fandou.coffee.learning.springcloud.admin.client.model.User;
import com.fandou.coffee.learning.springcloud.admin.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User get(Integer id){
        return userService.get(id);
    }

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    @PostMapping
    public int create(User user){
        return userService.create(user);
    }

    @PutMapping
    public int update(User user){
        return userService.update(user);
    }

    @PutMapping("/username")
    public int updateUsername(User user){
        return userService.updateUsername(user);
    }

    @PutMapping("/password")
    public int updatePassword(User user){
        return userService.updatePassword(user);
    }

    @DeleteMapping("/{id}")
    public int delete(Integer id){
        return userService.delete(id);
    }


}
