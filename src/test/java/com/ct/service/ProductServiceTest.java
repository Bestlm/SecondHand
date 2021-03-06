package com.ct.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

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