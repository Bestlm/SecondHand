package com.wu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.Product;
import com.wu.entity.User;
import com.wu.service.CartService;
import com.wu.service.ProductCategoryService;
import com.wu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.net.httpserver.HttpsServerImpl;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    /**
     *     查看商品  使用的是Resultful风格
     * @param type 类型的级别 分别是1 2 3
     * @param id  商品ID
     * @param session
     * @return
     */
    @GetMapping("/list/{type}/{id}")
    public ModelAndView findBylevel(@PathVariable("type") String type,
                                    @PathVariable("id") Integer id,
                                    HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        //通过种类的级别 查询所有商品
        modelAndView.addObject("productList",productService.findByCateGoryId(type,id));

        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVo());


        //6.参考productateGoryController下的listf方法
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));

        }
        return modelAndView;
    }

    //2.查看商品详情,根据商品名
    @GetMapping("/detail/{id}")
    public ModelAndView findByOne(@PathVariable("id") Integer id,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVo());
        modelAndView.addObject("productDetail",productService.getById(id));

        //6.参考productateGoryController下的listf方法
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));

        }
        return modelAndView;
    }

    //3.查看商品详情,根据商品图片,当然也可能没有,我们加一个试试
    @GetMapping("/detailpng/{png}")
    public ModelAndView findByOneByimg(@PathVariable("png") String png, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVo());
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("file_name",png);
        modelAndView.addObject("productDetail",productService.getOne(queryWrapper));
        //6.参考productateGoryController下的listf方法
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));

        }
        return modelAndView;
    }

}
