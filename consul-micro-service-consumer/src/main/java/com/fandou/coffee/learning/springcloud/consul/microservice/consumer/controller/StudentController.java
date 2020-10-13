package com.fandou.coffee.learning.springcloud.consul.microservice.consumer.controller;

import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;
import com.fandou.coffee.learning.springcloud.consul.microservice.consumer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public User getStudent(@PathVariable("id") String id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/list")
    public List<User> getStudents() {
        return studentService.getStudents();
    }
}
