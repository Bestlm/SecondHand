package com.ct.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ct.entity.Cart;
import com.ct.entity.ResultInfo;
import com.ct.entity.User;
import com.ct.service.CartService;
import com.ct.service.UserAddressService;
import com.ct.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//针对Cart的控制类
@Controller
@Slf4j
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartService cartService;

    //加入购物车功能, 商品id,商品价格,总共价钱
    //关于这个方法提交入口,在productDetail.js中 @PathVariable("productId") Integer productId,
    //                          @PathVariable("productPrice") Integer productPrice,
    //                          @PathVariable("quantity") Integer quantity,
    //                          @PathVariable("stock") Integer stock ,
    //{productId}/{productPrice}/{quantity}/{stock}
    @GetMapping("/add")
    public void addCart(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("开始准备加入购物车");
        Integer productId= Integer.valueOf(request.getParameter("productId"));
        //Integer productPrice=Integer.valueOf(request.getParameter("productPrice"));
        Float productPrice = Float.valueOf(request.getParameter("productPrice"));
        Integer quantity=Integer.valueOf(request.getParameter("quantity"));
        Integer stock=Integer.valueOf(request.getParameter("stock"));

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        ResultInfo info=new ResultInfo();
        if(stock<=0){
            info.setCode(0);
            info.setMsg("商品的库存不足！");
            out .write(JSON.toJSONString(info));
        }else {
            Cart cart = new Cart();
            cart.setProductId(productId).setQuantity(quantity).setCost((float) (productPrice * quantity));
            User user = (User) session.getAttribute("user");
            cart.setUserId(user.getId());
            //添加商品到购物车,并减库存,这里对save方法进行了重写
            try {
                cartService.save(cart);
                info.setCode(1);
                info.setMsg("添加到购物车成功！");
            } catch (Exception ex) {
                info.setCode(0);
                info.setMsg(ex.getMessage());
            } finally {
                out.write(JSON.toJSONString(info));
                out.flush();
                out.close();
            }
        }

    }

    /**
     * 查看购物车
     * @param session
     * @return
     */
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

