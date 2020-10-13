package com.fandou.coffee.learning.springcloud.admin.client.controller;

import com.fandou.coffee.learning.springcloud.admin.client.model.Api;
import com.fandou.coffee.learning.springcloud.admin.client.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/{id}")
    public Api get(@PathVariable("id") Integer id){
        return apiService.get(id);
    }

    @GetMapping("/list")
    public List<Api> list() {
        return apiService.list();
    }

    @PostMapping
    public int create(Api api){
        return apiService.create(api);
    }

    @PutMapping
    public int update(Api api){
        return apiService.update(api);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") Integer id){
        return apiService.delete(id);
    }
}
