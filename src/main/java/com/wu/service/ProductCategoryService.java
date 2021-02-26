package com.wu.service;

import com.wu.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wu.vo.ProductCategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
public interface ProductCategoryService{

    public List<ProductCategoryVo> getAllProductCategoryVo();

}
