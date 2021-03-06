package com.ct.controller;

import com.alibaba.fastjson.JSON;
import com.ct.entity.ProductCategory;
import com.ct.entity.User;
import com.ct.service.CartService;
import com.ct.service.ProductCategoryService;
import com.ct.utils.RedisUtils;
import com.ct.vo.ProductCategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/ProductCategory")
@Slf4j
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    RedisUtils redisUtils;

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


        if(redisUtils.exists("list"))
        {
            //判断如果缓存中有数据
            log.info("商品数据来自Redis");
            List<ProductCategoryVo> list = (List<ProductCategoryVo>) redisUtils.get("list");
            modelAndView.addObject("list",list);
        }else{
            //如果redis中没有数据
            log.info("商品数据来自数据库");
            List<ProductCategoryVo> allProductCategoryVo = productCategoryService.getAllProductCategoryVo();
            redisUtils.set("list",allProductCategoryVo);
            modelAndView.addObject("list",allProductCategoryVo);
        }

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

    /**
     * 三级联动中的通过父节点的id查找对应的子节点的信息
     * @param request
     * @param response
     */
    @GetMapping("/listCategory")
    public void listCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String id = request.getParameter("id");
        List<ProductCategory> categories;
        log.info("id是"+id);
        int parentId=0;
        if(id==null||id.equals("")){
            categories= new ArrayList<>();
        }else {
            parentId=Integer.valueOf(id);
            categories=productCategoryService.selectProductCategoryByParentId(parentId);
        }
        String categoriesString= JSON.toJSONString(categories);
        log.info("发布商品中查找到的商品种类"+categoriesString);
        response.getWriter().write(categoriesString);
    }

    @GetMapping("/findType")
    public void findTypeThree(HttpServletRequest request, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        List<ProductCategory> productCategoriesOne = productCategoryService.SelectProductCategoryListOne();
        List<ProductCategory> productCategoriesTwo= productCategoryService.SelectProductCategoryListTwo();
        List<ProductCategory> productCategoriesThree = productCategoryService.SelectProductCategoryListThree();
        HashMap<String,List<ProductCategory>> lists=new HashMap<>();
        lists.put("one",productCategoriesOne);
        lists.put("two",productCategoriesTwo);
        lists.put("three",productCategoriesThree);
        String jsonString = JSON.toJSONString(lists);
        log.info("种类:"+jsonString);
        response.getWriter().write(jsonString);
    }

}
