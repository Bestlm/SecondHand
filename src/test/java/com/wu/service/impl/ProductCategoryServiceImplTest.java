package com.wu.service.impl;

import com.wu.service.ProductCategoryService;
import com.wu.vo.ProductCategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    void getAllProductCategoryVo() {
        productCategoryService.getAllProductCategoryVo();
    }
}