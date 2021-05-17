package com.example.shrio_springboot.servie;

import com.example.shrio_springboot.pojo.User;

public interface UserService {
    User select(String username);
    int insert(User user);
}
