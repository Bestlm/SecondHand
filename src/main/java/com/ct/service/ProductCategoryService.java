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
 * @since 2020-12-29
 */
public interface ProductCategoryService{

    public List<ProductCategoryVo> getAllProductCategoryVo();

    List<ProductCategory> selectProductCategoryByParentId(int parentId);

    List<ProductCategory> SelectProductCategoryListThree();

    List<ProductCategory> SelectProductCategoryListOne();

    List<ProductCategory> SelectProductCategoryListTwo();
}
