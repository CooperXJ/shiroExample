package com.example.shrio_springboot.config.shiro;

import com.example.shrio_springboot.config.MySimpleByteSource;
import com.example.shrio_springboot.pojo.JwtToken;
import com.example.shrio_springboot.pojo.User;
import com.example.shrio_springboot.servie.UserService;
import com.example.shrio_springboot.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /*
     * 多重写一个support
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //认证
    //这个token就是从过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new NullPointerException("jwtToken 不允许为空");
        }
        //判断
        JwtUtil jwtUtil = new JwtUtil();
        if (!jwtUtil.isVerify(jwt)) {
            throw new UnknownAccountException();
        }
        //下面是验证这个user是否是真实存在的
        String username = (String) jwtUtil.decode(jwt).get("username");//判断数据库中username是否存在
        User user = userService.select(username);
        if(user==null){
            throw new UnknownAccountException("该用户不存在");
        }

        log.info("在使用token登录"+username);
        ByteSource byteSource = new MySimpleByteSource(username);
        return new SimpleAuthenticationInfo(jwt,user.getPassword(),byteSource,this.getName());
        //这里返回的是类似账号密码的东西，但是jwtToken都是jwt字符串。还需要一个该Realm的类名
    }


}