package com.fandou.coffee.learning.springcloud.consul.microservice.consumer.service;


import com.fandou.coffee.learning.springcloud.consul.microservice.common.model.User;

import java.util.List;

public interface StudentService {

    User getStudent(String id);

    List<User> getStudents();
}
