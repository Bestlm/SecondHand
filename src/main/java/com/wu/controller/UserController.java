package com.wu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.User;
import com.wu.service.CartService;
import com.wu.service.UserAddressService;
import com.wu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
//针对User的控制类
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserAddressService userAddressService;

    //1.登录功能
    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name",loginName);
        queryWrapper.eq("password",password);
        User user = userService.getOne(queryWrapper);
        if(user==null){
            return "login";
        }else{
            session.setAttribute("user",user);
            return "redirect:/ProductCategory/list";
        }
    }

    //2.注销功能
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    //3.注册功能
    @PostMapping("/register")
    public String register(User user, Model model){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name",user.getLoginName());
        User user1 = userService.getOne(queryWrapper);
        if(user1!=null){
            model.addAttribute("error",user.getLoginName()+"已存在，请重新输入！");
            return "register";
        }else{
            boolean b = userService.save(user);
            if(b==true){
                return "login";
            }else{
                return "register";
            }
        }
    }

    @Autowired
    private CartService cartService;
    //17.用户中心
    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        return modelAndView;
    }



//    //23.用户地址管理.
//    @GetMapping("/userAddressList")
//    public ModelAndView userAddressList(HttpSession session){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("userAddressList");
//        User user = (User) session.getAttribute("user");
//        modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
//        //地址管理就要查询用户的所有地址，user_address数据库
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("user_id",user.getId());
//        modelAndView.addObject("userAddressList",userAddressService.list(queryWrapper));
//        return modelAndView;
//    }
}
