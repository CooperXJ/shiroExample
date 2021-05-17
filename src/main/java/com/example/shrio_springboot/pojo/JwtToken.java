package com.example.shrio_springboot.pojo;

import com.example.shrio_springboot.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationToken;

//这个就类似UsernamePasswordToken
public class JwtToken implements AuthenticationToken {

    private String jwt;
    private JwtUtil jwtUtil = new JwtUtil();
    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    @Override//类似是用户名
    public String getPrincipal() {

        return jwt;
    }

    @Override//类似密码
    public String getCredentials() {
        return (String)jwtUtil.decode(jwt).get("password");
    }
    //返回的都是jwt
}