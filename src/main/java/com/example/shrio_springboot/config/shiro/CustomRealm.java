package com.example.shrio_springboot.config.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    private static final Set<String> tomRoleNameSet = new HashSet<>();
    private static final Set<String> tomPermissionNameSet = new HashSet<>();
    private static final Set<String> jerryRoleNameSet = new HashSet<>();
    private static final Set<String> jerryPermissionNameSet = new HashSet<>();
    static {
        tomRoleNameSet.add("admin");
        jerryRoleNameSet.add("user");
        tomPermissionNameSet.add("user:insert");
        tomPermissionNameSet.add("user:update");
        tomPermissionNameSet.add("user:delete");
        tomPermissionNameSet.add("user:query");
        jerryPermissionNameSet.add("user:query");
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        if (username.equals("tom")) {
            info.addRoles(tomRoleNameSet);
            info.addStringPermissions(tomPermissionNameSet);
        } else if (username.equals("jerry")) {
            info.addRoles(jerryRoleNameSet);
            info.addStringPermissions(jerryPermissionNameSet);
        }
        return info;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        if (username == null)
            throw new UnknownAccountException("用户名不能为空");
        SimpleAuthenticationInfo info = null;
        if (username.equals("tom"))
            return new SimpleAuthenticationInfo("tom", "123", CustomRealm.class.getName());
        else if (username.equals("jerry"))
            return new SimpleAuthenticationInfo("jerry", "123", CustomRealm.class.getName());
        else
            return null;
    }
}