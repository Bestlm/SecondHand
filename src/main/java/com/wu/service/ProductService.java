package com.wu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wu.entity.Product;
import com.wu.entity.User;

import java.security.SecureRandom;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
public interface ProductService extends IService<Product> {


    public List<Product> findByCateGoryId(String type, Integer id);

}
