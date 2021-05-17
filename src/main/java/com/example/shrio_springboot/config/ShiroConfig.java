package com.example.shrio_springboot.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);

        /**
         * anon 无需认证：匿名访问
         * authc：必须认证才能访问
         * user: 必须拥有记住我功能才能使用
         * perms：拥有某个资源的权限才能访问
         * role：拥有某个角色的权限才能访问
         */
        //添加shiro内置过滤器
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/index","anon");
        filterMap.put("/add","perms[user:add]");
        filterMap.put("/update","perms[user:update]");
        bean.setFilterChainDefinitionMap(filterMap);

        //设置登录界面
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }

    //创建SecurityManager
    @Bean
    public SecurityManager getSecurityManager(@Qualifier(value = "customRealm") CustomRealm customRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(customRealm);
        return securityManager;
    }

    //创建realm
    @Bean(name = "customRealm")
    public CustomRealm getCustomRealm(){
        CustomRealm customRealm = new CustomRealm();
        //设置缓存
        customRealm.setCacheManager(new EhCacheManager());
        //开启全局缓存
        customRealm.setCachingEnabled(true);
        customRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
        customRealm.setAuthenticationCacheName("authenticationCacheCache");//取名字
        customRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
        customRealm.setAuthorizationCacheName("authorizationCache");//取名字
        return customRealm;
    }
}
