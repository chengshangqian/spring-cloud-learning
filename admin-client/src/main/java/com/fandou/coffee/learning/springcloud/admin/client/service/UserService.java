package com.fandou.coffee.learning.springcloud.admin.client.service;

import com.fandou.coffee.learning.springcloud.admin.client.dao.UserDao;
import com.fandou.coffee.learning.springcloud.admin.client.model.Role;
import com.fandou.coffee.learning.springcloud.admin.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.getByUsername(username);
    }

    public User get(Integer id){
        return userDao.get(id);
    }

    public List<User> list(){
        return userDao.list();
    }

    public int create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.create(user);
    }

    public int update(User user){
        return userDao.update(user);
    }

    public int updateUsername(User user){
        return userDao.updateUsername(user);
    }

    public int updatePassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.updatePassword(user);
    }

    public int delete(Integer id){
        return userDao.delete(id);
    }

    private User getMock(Integer id){
        // TODO 从数据库中查询用户及其角色信息
        // 模拟用户信息
        User user = new User();
        user.setId(1);
        user.setUsername("成九五");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(true);

        // 模拟角色信息
        Set<Role> roles = new HashSet<>(4);
        Role admin = new Role();
        admin.setId(1);
        admin.setName("超级管理员");
        admin.setCode("ROLE_ADMIN");
        roles.add(admin);

        Role system = new Role();
        system.setId(2);
        system.setName("系统管理员");
        system.setCode("ROLE_SYSTEM");
        roles.add(system);

        Role pm = new Role();
        pm.setId(3);
        pm.setName("产品经理");
        pm.setCode("ROLE_PM");
        roles.add(pm);

        Role u = new Role();
        u.setId(4);
        u.setName("一般用户");
        u.setCode("ROLE_USER");
        roles.add(u);

        user.setRoles(roles);

        System.out.println("UserDetails ====> " + user);

        return user;
    }
}
