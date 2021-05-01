package com.example.shrio_springboot.servie.Impl;

import com.example.shrio_springboot.mapper.UserMapper;
import com.example.shrio_springboot.pojo.User;
import com.example.shrio_springboot.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User select(String username) {
        return userMapper.selectById(username);
    }
}
