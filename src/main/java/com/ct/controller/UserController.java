package com.ct.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ct.entity.ResultInfo;
import com.ct.entity.User;
import com.ct.enums.GenderEnum;
import com.ct.service.CartService;
import com.ct.service.UserAddressService;
import com.ct.service.UserService;
import com.ct.utils.MD5Util;
import com.ct.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
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
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserAddressService userAddressService;

    //1.登录功能
    @PostMapping("/login")
    public ModelAndView login(String loginName, String password, HttpSession session){
       /* QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name",loginName);
        queryWrapper.eq("password",password);
        User user = userService.getOne(queryWrapper);
        if(user==null){
            return "login";
        }else{
            session.setAttribute("user",user);
            return "redirect:/ProductCategory/list";
        }*/
       ModelAndView modelAndView=new ModelAndView();
        UsernamePasswordToken token=new UsernamePasswordToken(loginName,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (Exception ex){
            modelAndView.addObject("error",ex.getMessage());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        User user=new User();
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(principal!=null){
            BeanUtils.copyProperties(principal,user);
        }
        session.setAttribute("user",user);
        modelAndView.addObject(new ResultInfo(1,"登录成功"));
        modelAndView.setViewName("redirect:/ProductCategory/list");
        return modelAndView;
    }

    //2.注销功能
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    /**
     * 用户注册功能
     */
    @PostMapping("/register")
    public String register(UserVO uservo, Model model){
        QueryWrapper queryWrapper = new QueryWrapper();
        log.info("uservo"+uservo.toString());
        User user=new User();
        BeanUtils.copyProperties(uservo,user);
        log.info("user"+user.toString());
        if(uservo.getGender().equals("男")){
            user.setGender(GenderEnum.MAN);
        }else {
            user.setGender(GenderEnum.WOMAN);
        }
        queryWrapper.eq("login_name",user.getLoginName());
        User user1 = userService.getOne(queryWrapper);
        if(user1!=null){
            model.addAttribute("error",user.getLoginName()+"已存在，请重新输入！");
            return "register";
        }else{
            String password= user.getPassword();

            String md5ofPassword = new MD5Util().getMD5ofStr(password);
            user.setPassword(md5ofPassword);
            log.info("password(MD5)"+md5ofPassword);
            user.setFileName("10.jpg");
            boolean b = userService.save(user);
            if(b==true){
                return "login";
            }else{
                model.addAttribute("error","保存用户信息出错,请重新输入");
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
