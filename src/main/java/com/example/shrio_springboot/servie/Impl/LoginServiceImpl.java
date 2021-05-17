package com.example.shrio_springboot.servie.Impl;

import com.example.shrio_springboot.servie.LoginService;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {


    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public String jwtLogin(String username, String password) {
        Assert.notNull(username, "用户名不能为空");
        Assert.notNull(password, "密码不能为空");

        return JWTUtil.sign(username,password);
    }
}