package com.ct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ct.entity.Product;
import com.ct.entity.ProductCategory;

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

    /**
     * 根据名字进行模糊查询
     * @param KeyWord
     * @return
     */
    public List<Product> findByKeyWord(String KeyWord);


    List<ProductCategory> getProductCategory3();

    Product findByTypeThree(Integer categorylevelthreeId);
}
