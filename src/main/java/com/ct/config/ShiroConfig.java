package com.ct.config;

import com.ct.realm.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
@Slf4j
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //配置未登录时拦截到的路径
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        LinkedHashMap<String,String> filterChainDefinitionMap =new LinkedHashMap<>();
        filterChainDefinitionMap.put("/cart/**","authc");
        filterChainDefinitionMap.put("/orders/**","authc");
        filterChainDefinitionMap.put("/order/**","authc");
        filterChainDefinitionMap.put("/product/**","authc");
        filterChainDefinitionMap.put("/user/userInfo","authc");
        filterChainDefinitionMap.put("/user/logout","authc");
        filterChainDefinitionMap.put("/userAddress/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;

    }


    @Bean
    public SecurityManager getSecurityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(getUserRealm());
        return securityManager;
    }

    @Bean
    public UserRealm getUserRealm(){
        return new UserRealm();
    }


}
