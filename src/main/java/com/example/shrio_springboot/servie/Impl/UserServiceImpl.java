package com.example.shrio_springboot.servie.Impl;

import com.example.shrio_springboot.mapper.UserMapper;
import com.example.shrio_springboot.pojo.User;
import com.example.shrio_springboot.servie.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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

    @Override
    public int insert(User user) {
        user.setPassword(encodePassword(user.getUsername(), user.getPassword()));
        return userMapper.insert(user);
    }

    private String encodePassword(String username,String password){
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        String credential = password;//密码
        String hashAlgorithmName = "MD5";//加密方式
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credential,
                credentialsSalt, 1024);
        return simpleHash.toString();
    }
}
