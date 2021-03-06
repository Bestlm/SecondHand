package com.ct.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ct.entity.User;
import com.ct.service.UserService;
import com.ct.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 授权  TODO 暂时没有进行权限管理
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("---------------------开始授权---------------------");
        return null;
    }

    /**
     * 认证
     * @param authenticationToken 认证令牌
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("---------------------开始认证---------------------");
        // 根据 Token 获取用户名
        String username = (String) authenticationToken.getPrincipal();
        String password=new String((char[]) authenticationToken.getCredentials());
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>();
        queryWrapper.eq("login_name",username);
        log.info("login_name:"+username+"   password:"+password);
        User selectUser = userService.getOne(queryWrapper);

        if(selectUser==null){
            throw new UnknownAccountException("账号不存在");
        }
        if (!selectUser.getPassword().equalsIgnoreCase(new MD5Util().getMD5ofStr(password))){
            throw new UnknownAccountException("密码错误");
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(selectUser,password,getName());
        log.info("---------------------认证成功---------------------");
        return simpleAuthenticationInfo;
    }
}
