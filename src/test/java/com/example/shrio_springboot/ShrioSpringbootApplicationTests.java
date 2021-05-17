package com.example.shrio_springboot;

import com.example.shrio_springboot.pojo.User;
import com.example.shrio_springboot.servie.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShrioSpringbootApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("cooper");
        user.setPassword("123456");
        user.setDisabled(false);
        user.setPerms("user:*");
         userService.insert(user);
    }

}
