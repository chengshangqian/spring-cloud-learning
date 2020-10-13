package com.fandou.coffee.learning.springcloud.consul.microservice.consumer.service;

import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public User getStudent(String id) {
        return remoteUserService.get(id);
    }

    @Override
    public List<User> getStudents() {
        return remoteUserService.list();
    }
}
