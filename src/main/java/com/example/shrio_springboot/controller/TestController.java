package com.example.shrio_springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping(value = {"","/index"})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/add")
    public String add(){
        return "add";
    }

    @RequestMapping(value = "/update")
    public String update(){
        return "update";
    }

    @RequestMapping(value = "/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(String username, String password, Model model){
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        //封装当前用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try{
            subject.login(token);//执行登录方法，如果并捕获异常
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    @RequestMapping(value = "/noauth")
    @ResponseBody
    public String noauth(){
        return "noauth";
    }
}
