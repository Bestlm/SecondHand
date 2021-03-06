package com.ct.service.impl;

import com.ct.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    void getAllProductCategoryVo() {
        productCategoryService.getAllProductCategoryVo();
    }
}