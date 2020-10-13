package com.fandou.coffee.learning.springcloud.oauth2.jwt.client.service;

import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.dao.UserDao;
import com.fandou.coffee.learning.springcloud.oauth2.jwt.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User get(Integer id) {
        return userDao.get(id);
    }

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public int create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.create(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int updateUsername(User user) {
        return userDao.updateUsername(user);
    }

    @Override
    public int updatePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.updatePassword(user);
    }

    @Override
    public int delete(Integer id) {
        return userDao.delete(id);
    }
}
