package com.wu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.entity.Product;
import com.wu.entity.ProductCategory;
import com.wu.entity.User;
import com.wu.mapper.ProductCategoryMapper;
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
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findByCateGoryId(String type, Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("categorylevel"+type+"_id",id);
        List<Product> productList = productMapper.selectByMap(map);
        return productList;
    }

    @Override
    public List<Product> findByKeyWord(String KeyWord) {
        QueryWrapper<Product> queryWrapper=new QueryWrapper<Product>();
        queryWrapper.like("name",KeyWord);
        List<Product> products = baseMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 获取所有的种类3
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategory3() {
        QueryWrapper<ProductCategory> queryWrapper=new QueryWrapper<ProductCategory>();
        queryWrapper.eq("type",3);
        List<ProductCategory> productCategories3 = productCategoryMapper.selectList(queryWrapper);
        return productCategories3;
    }

    @Override
    public Product findByTypeThree(Integer categorylevelthreeId) {
        QueryWrapper<Product> queryWrapper=new QueryWrapper();
        queryWrapper.eq("categorylevelthree_id",categorylevelthreeId);
        Product product = baseMapper.selectOne(queryWrapper);
        return product;
    }


}
