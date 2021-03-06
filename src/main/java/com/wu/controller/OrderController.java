package com.wu.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.*;
import com.wu.mapper.OrderDetailMapper;
import com.wu.service.CartService;
import com.wu.service.OrderDetailService;
import com.wu.service.OrderService;
import com.wu.service.ProductService;
import com.wu.vo.OrderDetailVO;
import com.wu.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@Slf4j
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


    //这是进入第三个页面的 方法，开始跟order相关,下单操作,,这个最好封装起来，我没有封装，这是一个bug
    /**
     * 我的订单
     * @param session
     * @return
     */
    @GetMapping("/orderList")
    public ModelAndView orderList(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        User user = (User) session.getAttribute("user");
        //查询所有的购物车的数据
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        //19.查询订单信息--order数据库,获得订单号、地址、总价钱
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        //获取到用户的订单信息  TODO 这里应该是获取所有的订单,用户可能被提交了
/*        Order orders = orderService.getOne(queryWrapper);*/
        List<Order> orders = orderService.list(queryWrapper);
        modelAndView.addObject("orderMsg",orders);
        //如果这里order是List的话，就要使用其他的判空方式
        if(orders==null||orders.size()==0){
            modelAndView.addObject("productMsg",new ArrayList<>());
        }else {
            //对每一个订单都遍历
            List<OrderVO>  orderVOS=new ArrayList<>();

            for (Order order:orders) {
                OrderVO orderVO=new OrderVO();
                BeanUtils.copyProperties(order,orderVO);
                //给orderVo中的List<OrderDetailVO>对象赋值
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("order_id",order.getId());
                List<OrderDetail> orderDetails = orderDetailService.list(queryWrapper);
                List<OrderDetailVO> orderDetailVOS = orderDetails.stream().map(orderDetail -> {
                    OrderDetailVO orderDetailVO = new OrderDetailVO();
                    BeanUtils.copyProperties(orderDetail, orderDetailVO);
                    QueryWrapper<Product> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("id", orderDetailVO.getProductId());
                    Product one = productService.getOne(queryWrapper1);
                    orderDetailVO.setName(one.getName());
                    orderDetailVO.setFileName(one.getFileName());
                    orderDetailVO.setPrice(one.getPrice());
                    return orderDetailVO;
                }).collect(Collectors.toList());
                orderVO.setOrderDetailVOS(orderDetailVOS);
                orderVOS.add(orderVO);
            }
            modelAndView.addObject("productMsg",orderVOS);
            log.info("所有订单的所有数据"+JSON.toJSONString(orderVOS));
        }
        return modelAndView;
    }

}
