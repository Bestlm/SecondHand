package com.wu.controller;

import com.wu.entity.User;
import com.wu.service.CartService;
import com.wu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
@RequestMapping("/ProductCategory")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;

    /**
     * 获取商品分级的信息
     * @param session
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");

        //查询到的3个级别的种类名称
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVo());

        //这是对于首页购物车的查询封装，这是靠后才去考虑的,但是直接访问这个就会报错，因为需要登陆,所以我们加个条件，让这个购物车去显示，没登陆就不显示了
        //参考productateGoryController下的listf方法
        //TODO  没有登录也可以加入购物车  可以将没有登录的购物车中的数据存放在cookie中   或者如果没有登录  就不会显示购物车 这个图标
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        }
        //当我们写好这个以后，对于其他页面点击，仍然会报错，所以我们要把这几行代码给其他页面也加上，未登录和已登录
        return modelAndView;

    }


}
