package com.wu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.entity.ProductCategory;
import com.wu.entity.User;
import com.wu.mapper.ProductCategoryMapper;
import com.wu.mapper.UserMapper;
import com.wu.service.ProductCategoryService;
import com.wu.service.UserService;
import com.wu.vo.ProductCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
