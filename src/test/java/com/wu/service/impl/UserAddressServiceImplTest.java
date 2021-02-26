package com.wu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.mapper.UserAddressMapper;
import com.wu.service.UserAddressService;
import com.wu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.ws.soap.Addressing;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAddressServiceImplTest {


    @Autowired
    private UserAddressService userAddressService;

    @Test
    void getUserAddress() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",16);
        userAddressService.list(queryWrapper).forEach(System.out::println);
    }
}