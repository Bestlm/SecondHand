package com.wu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void findBylevelId(){
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevelthree_id",655);
        productService.listByMap(map).forEach(System.out::println);
    }
}