package com.fandou.coffee.learning.springcloud.admin.client.controller;

import com.fandou.coffee.learning.springcloud.admin.client.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    /**
     * 应用首页
     *
     * @return
     */
    @GetMapping({"/","/index.htm","/index.html"})
    public String root() {
        System.out.println("根目录...");

        // 服务器内部跳转，不改变浏览器地址
        return "forward:/index";
    }

    /**
     * 应用首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index() {
        System.out.println("首页...");
        return "index";
    }

    /**
     * @AuthenticationPrincipal 注解注入的是一个UsernamePasswordAuthenticationToken实例，它是认证信息Authentication接口的实现，
     * Authentication接口继承了Principal接口。Authentication接口实现会包含了五大部分：
     * 1.Principal：主体，即认证对象，它可能是UserDetails、AuthenticatedPrincipal、Principal的实现，比如继承了UserDetails的User
     * 2.GrantedAuthority：授权
     * 3.Credentials：凭证即密码
     * 4、Details：请求详情，包含了IP地址等
     * 5、Authenticated：认证状态，如果通过，为true
     *
     * @param authentication
     * @return
     */
    @GetMapping("/user/index")
    public String userIndex(@AuthenticationPrincipal Authentication authentication) {
        System.out.println("authentication => " + authentication.getClass().getSimpleName());
        System.out.println("principal => " + authentication.getPrincipal().getClass().getSimpleName());

        if(authentication.getPrincipal() instanceof User){
            User user = (User) authentication.getPrincipal();
            System.out.println("user => " + user);
        }

        return "user/index";
    }

    @GetMapping("/admin/index")
    public String adminIndex(@AuthenticationPrincipal Authentication authentication) {
        System.out.println("authentication => " + authentication.getClass().getSimpleName());
        System.out.println("principal => " + authentication.getPrincipal().getClass().getSimpleName());

        if(authentication.getPrincipal() instanceof User){
            User user = (User) authentication.getPrincipal();
            System.out.println("user => " + user);
        }

        return "admin/index";
    }

    /**
     * 自定义登录页面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
