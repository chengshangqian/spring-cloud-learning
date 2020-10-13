package com.fandou.coffee.learning.springcloud.admin.client.service;

import com.fandou.coffee.learning.springcloud.admin.client.dao.RoleDao;
import com.fandou.coffee.learning.springcloud.admin.client.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> listByUserId(Integer userId){
        return roleDao.listByUserId(userId);
    }

    public List<Role> listByApiId(Integer apiId){
        return roleDao.listByApiId(apiId);
    }

    public Role get(Integer id){
        return roleDao.get(id);
    }

    /**
     * 从数据库或缓存中查询所有API
     *
     * @return 返回API列表
     */
    public List<Role> list() {
        return roleDao.list();
    }

    public int create(Role role){
        return roleDao.create(role);
    }

    public int update(Role role){
        return roleDao.update(role);
    }

    public int delete(Integer id){
        return roleDao.delete(id);
    }
}
