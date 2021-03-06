package com.ct.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ct.entity.User;
import com.ct.mapper.UserMapper;
import com.ct.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
