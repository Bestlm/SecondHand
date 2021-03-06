package com.ct.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryVo implements Serializable {
    /**
     * 商品种类的ID
     */
    private Integer id;

    /**
     * 商品种类的名字
     */
    private String name;

    /**
     * 属于该商品种类的子种类
     */
    private List<ProductCategoryVo> children;


    /**
     * 商品的图片
     */
    private String bannerImg;

    private String topImg;

    /**
     * 属于该分类的商品
     */
    private List<ProductVO> productVOList;

    public ProductCategoryVo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
