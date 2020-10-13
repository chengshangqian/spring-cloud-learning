package com.fandou.coffee.learning.springcloud.admin.client.service;

import com.fandou.coffee.learning.springcloud.admin.client.dao.ApiDao;
import com.fandou.coffee.learning.springcloud.admin.client.model.Api;
import com.fandou.coffee.learning.springcloud.admin.client.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApiService {

    @Autowired
    private ApiDao apiDao;

    public Api get(Integer id){
        return apiDao.get(id);
    }

    /**
     * 从数据库或缓存中查询所有API
     *
     * @return 返回API列表
     */
    public List<Api> list() {
        return apiDao.list();
    }

    public int create(Api api){
        return apiDao.create(api);
    }

    public int update(Api api){
        return apiDao.update(api);
    }

    public int delete(Integer id){
        return apiDao.delete(id);
    }

    private List<Api> listMock(){
        List<Api> result = new ArrayList<>(2);

        //-------- 用户服务API --------
        Api userApiMock = new Api();
        userApiMock.setId(1);
        userApiMock.setName("用户服务"); // API名称
        userApiMock.setPathAntPattern("/user/**"); // url模式
        userApiMock.setPath("/user/index"); // 具体访问路径

        // 模拟两个角色
        Set<Role> roles = new HashSet<>(2);
        Role pm = new Role();
        pm.setId(3);
        pm.setName("产品经理");
        pm.setCode("ROLE_PM");

        Role u = new Role();
        u.setId(4);
        u.setName("一般用户");
        u.setCode("ROLE_USER");

        roles.add(pm);
        roles.add(u);

        userApiMock.setRoles(roles);

        // 放入apis
        result.add(userApiMock);

        //-------- 管理员服务API --------
        Api adminApiMock = new Api();
        adminApiMock.setId(2);
        adminApiMock.setName("管理员服务"); // API名称
        adminApiMock.setPathAntPattern("/admin/**"); // url模式
        adminApiMock.setPath("/admin/index"); // 具体访问路径

        // 模拟一个角色
        Set<Role> rolez = new HashSet<>(1);
        Role admin = new Role();
        admin.setId(1);
        admin.setName("超级管理员");
        admin.setCode("ROLE_ADMIN");

        rolez.add(admin);

        adminApiMock.setRoles(rolez);

        // 放入apis
        result.add(adminApiMock);

        return result;
    }
}
