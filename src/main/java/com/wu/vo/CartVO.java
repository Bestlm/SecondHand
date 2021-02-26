package com.wu.vo;

import lombok.Data;

@Data
public class CartVO {
    //我们加入购物车跳转的界面需要的数据展示,不是一个实体类能够满足的,所以进行封装,并在cartServive中去自定义我们的方法

    //这三个从cart中获取
    private Integer id;
    private Integer quantity;
    private Float cost;

    private Integer productId;

    //这三个从product中获取
    private String name;
    private Float price;
    private String fileName;

    //8.
    private Integer stock;

}
