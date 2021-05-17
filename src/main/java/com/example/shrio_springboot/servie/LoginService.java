package com.example.shrio_springboot.servie;

/**
 * @author 薛进
 * @version 1.0
 * @Description
 * @date 2021/5/17 8:49 下午
 */
public interface LoginService {
    String jwtLogin(String username, String password);
}
