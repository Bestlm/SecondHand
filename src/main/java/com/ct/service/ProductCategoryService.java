package com.ct.service;

import com.ct.entity.ProductCategory;
import com.ct.vo.ProductCategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈腾
 * @since 2021-2-19
 */
public interface ProductCategoryService{

    public List<ProductCategoryVo> getAllProductCategoryVo();

    List<ProductCategory> selectProductCategoryByParentId(int parentId);

    List<ProductCategory> SelectProductCategoryListThree();

    List<ProductCategory> SelectProductCategoryListOne();

    List<ProductCategory> SelectProductCategoryListTwo();
}
