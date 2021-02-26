package com.wu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.User;
import com.wu.entity.UserAddress;
import com.wu.service.CartService;
import com.wu.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/address")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private CartService cartService;
    //23.用户地址管理.
    @GetMapping("/userAddressList")
    public ModelAndView userAddressList(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        //地址管理就要查询用户的所有地址，user_address数据库
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        modelAndView.addObject("userAddressList",userAddressService.list(queryWrapper));
        return modelAndView;
    }
//地址删除
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        userAddressService.removeById(id);
        return "redirect:/address/userAddressList";
    }
//地址修改,我没写
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Integer id){
        userAddressService.update();
        return "redirect:/address/userAddressList";
    }
}
