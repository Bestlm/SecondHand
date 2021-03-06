package com.ct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ct.service.UserAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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