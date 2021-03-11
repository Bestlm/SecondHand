package com.ct.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ct.entity.Order;
import com.ct.entity.OrderDetail;
import com.ct.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CartServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;


    @Test
    void findAllCartVoByUserId() {
        //19.查询订单信息--order数据库,获得订单号、地址、总价钱
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",16);
        Order orders = orderService.getOne(queryWrapper);
        //Order(userAddress=天安门, cost=17688.0, serialnumber=111085

//        //20.根据订单id，查看订单所有商品---order_detail数据库，获得每个商品购买数量、每个商品总价
        List<OrderDetail> orderDetails = new ArrayList<>();
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orders.getId());
        List<OrderDetail> productlist = orderDetailService.list(queryWrapper);
//        productlist.forEach(System.out::println);
       // 结果：OrderDetail(quantity=3, cost=17688.0)
        //21.根据20查到的product_id,去查找商品的详细信息，所以要遍历
        List<Product> product = new ArrayList<>();
        for (OrderDetail productDetail : productlist) {
            //22.根据商品id去查
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",productDetail.getProductId());
            Product product1 = productService.getOne(queryWrapper);
            product.add(product1);
        }
        product.forEach(System.out::println);
       //结果： Product( name=婴儿果粉,  price=5896.0, fileName=milk_1.jpg)

//        modelAndView.addObject("productMsg2",productlist);
    }
}