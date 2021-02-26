package com.wu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.entity.Product;
import com.wu.entity.User;
import com.wu.mapper.ProductMapper;
import com.wu.mapper.UserMapper;
import com.wu.service.ProductService;
import com.wu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findByCateGoryId(String type, Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",id);
        List<Product> productList = productMapper.selectByMap(map);
        return productList;
    }
}
