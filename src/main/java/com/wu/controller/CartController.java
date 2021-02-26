package com.wu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.Cart;
import com.wu.entity.Order;
import com.wu.entity.User;
import com.wu.service.CartService;
import com.wu.service.OrderService;
import com.wu.service.UserAddressService;
import com.wu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

//针对Cart的控制类
@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;

    //加入购物车功能, 商品id,商品价格,总共价钱
    //关于这个方法提交入口,在productDetail.js中
    @GetMapping("/add/{productId}/{productPrice}/{quantity}")
    public String addCart(@PathVariable("productId") Integer productId,
                                @PathVariable("productPrice") Integer productPrice,
                                @PathVariable("quantity") Integer quantity,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Cart cart = new Cart();
        cart.setProductId(productId).setQuantity(quantity).setCost((float) (productPrice*quantity));
       //这个地方我们去配一个过滤器,让没有登录的人返回到/ProductCateGroy/list页面,不能加入购物车
        //过滤器名字UserFilter,还要写个config:FilterConfig,其实我们也可以用spring security只不过我不会
        User user = (User) session.getAttribute("user");
        cart.setUserId(user.getId());
        //添加商品到购物车,并减库存,这里对save方法进行了重写

        //这是手动跑出异常,你看看save方法还有呢异常处理,吧这个try catch删除,save的就可以生效
        try {
            if(cartService.save(cart)){
                return "redirect:/cart/findAllCartVo";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/ProductCategory/list";
        }
        return null;
    }

    @GetMapping("/findAllCartVo")
    public ModelAndView findAllCartVoByUserId(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        return modelAndView;
    }

    //9.写购物车订单页面1的删除操作
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        cartService.removeById(id);
        return "redirect:/cart/findAllCartVo";
    }
    @Autowired
    private UserService userService;
    @Autowired
    private UserAddressService userAddressService;
//10这是确认订单的controller，
    @GetMapping("/settlement2")
    public ModelAndView settlement2(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        User user = (User) session.getAttribute("user");
        //查看订单信息
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));

//        //获取用户信息,这个session中已经存在
//       modelAndView.addObject("userMsg",userService.getById(user.getId()));

       //获取用户地址address
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        modelAndView.addObject("userAddress",userAddressService.list(queryWrapper));

        return modelAndView;
    }



    //12更新订单商品信息
    @PostMapping("/updateCart/{id}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(@PathVariable("id") Integer id,
                             @PathVariable("quantity") Integer quantity,
                             @PathVariable("cost") Float cost){
        Cart cart = cartService.getById(id);
        cart.setQuantity(quantity).setCost(cost);
        if(cartService.updateById(cart)){
            return "success";
        }else{
            return "fail";
        }
    }


}

