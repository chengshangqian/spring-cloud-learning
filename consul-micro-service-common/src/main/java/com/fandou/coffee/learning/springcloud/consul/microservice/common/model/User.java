package com.fandou.coffee.learning.springcloud.consul.microservice.common.model;

public class User {
    private String id;
    private String name;

    // 测试用
    private String port;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
