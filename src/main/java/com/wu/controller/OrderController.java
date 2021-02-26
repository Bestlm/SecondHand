package com.wu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.*;
import com.wu.mapper.OrderDetailMapper;
import com.wu.service.CartService;
import com.wu.service.OrderDetailService;
import com.wu.service.OrderService;
import com.wu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    //13.这是进入第三个页面的 方法，开始跟order相关,下单操作
    @PostMapping("/settlement3")
    public ModelAndView settlement3(Order order, String address,String remark,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
       //订单入库，并且将新地址也更新并入库
        orderService.save(order,user,address,remark);


        //15.这是settlement3页面用的数据，也就是查询订单数据库
        modelAndView.addObject("orderMsg",order);
        return modelAndView;
    }
    //18.这是进入第三个页面的 方法，开始跟order相关,下单操作,,这个最好封装起来，我没有封装，这是一个bug
    @GetMapping("/orderList")
    public ModelAndView orderList(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        //19.查询订单信息--order数据库,获得订单号、地址、总价钱
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        Order orders = orderService.getOne(queryWrapper);
        //结果：Order(userAddress=东莞松山湖, cost=17688.0, serialnumber=111085
        modelAndView.addObject("orderMsg",orders);
        //20.根据订单id，查看订单所有商品---order_detail数据库，获得每个商品购买数量、每个商品总价
        List<OrderDetail> orderDetails = new ArrayList<>();
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id",orders.getId());
        List<OrderDetail> productlist = orderDetailService.list(queryWrapper);
//        productlist.forEach(System.out::println);
        // 结果：OrderDetail(quantity=3, cost=17688.0)
        modelAndView.addObject("productMsg1",productlist);
        //21.根据20查到的product_id,去查找商品的详细信息，所以要遍历
        List<Product> product = new ArrayList<>();
        for (OrderDetail productDetail : productlist) {
            //22.根据商品id去查
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",productDetail.getProductId());
            Product product2 = productService.getOne(queryWrapper);
            product.add(product2);
        }
        //结果： Product( name=婴儿果粉,  price=5896.0, fileName=milk_1.jpg)
        modelAndView.addObject("productMsg2",product);
        return modelAndView;
    }

}
