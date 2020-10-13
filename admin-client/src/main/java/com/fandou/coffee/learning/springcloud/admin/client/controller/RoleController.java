package com.fandou.coffee.learning.springcloud.admin.client.controller;

import com.fandou.coffee.learning.springcloud.admin.client.model.Role;
import com.fandou.coffee.learning.springcloud.admin.client.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list/{userId}")
    public List<Role> listByUserId(@PathVariable("userId") Integer userId){
        return roleService.listByUserId(userId);
    }

    @GetMapping("/list/{apiId}")
    public List<Role> listByApiId(@PathVariable("apiId") Integer apiId){
        return roleService.listByApiId(apiId);
    }

    @GetMapping("/{id}")
    public Role get(@PathVariable("id") Integer id){
        return roleService.get(id);
    }

    @GetMapping("/list")
    public List<Role> list() {
        return roleService.list();
    }

    @PostMapping
    public int create(Role role){
        return roleService.create(role);
    }

    @PutMapping
    public int update(Role role){
        return roleService.update(role);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") Integer id){
        return roleService.delete(id);
    }
}
