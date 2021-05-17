package com.example.shrio_springboot.controller;

import com.example.shrio_springboot.pojo.User;
import com.example.shrio_springboot.servie.UserService;
import com.example.shrio_springboot.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(String username,String password) {
        log.info("username:{},password:{}",username,password);
        Map<String, String> map = new HashMap<>();
//        if (!"tom".equals(username) || !"123".equals(password)) {
//            map.put("msg", "用户名密码错误");
//            return map;
//        }
        User user = userService.select(username);
        if(user==null){
            map.put("msg", "用户不存在");
            return map;
        }

        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", username);
        chaim.put("password",password);
        String jwtToken = jwtUtil.encode(username, 60 * 60 * 1000, chaim);
        map.put("msg", "登录成功");
        map.put("token", jwtToken);
        return map;
    }


    @RequestMapping("/testdemo")
    public ResponseEntity<String> testdemo() {
        return ResponseEntity.ok("我爱蛋炒饭");
    }

}
