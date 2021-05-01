package com.example.shrio_springboot.config;

import com.example.shrio_springboot.pojo.User;
import com.example.shrio_springboot.servie.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //获取session中的user对象
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        //赋权
        info.addStringPermission(principal.getPerms());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo");
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //查询数据库中的用户
        User user = userService.select(token.getUsername());

        if(user==null){
            return null;
        }

        //密码认证shiro来做
        //第一次参数作为传递使用的，目的是授权的时候使用，第二个是需要比对的真正的密码，就是数据库中保存的密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
