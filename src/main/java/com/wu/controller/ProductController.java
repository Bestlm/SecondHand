package com.wu.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.Product;
import com.wu.entity.ResultInfo;
import com.wu.entity.User;
import com.wu.service.CartService;
import com.wu.service.ProductCategoryService;
import com.wu.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sun.net.httpserver.HttpsServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
@Slf4j
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


        //获取用户 TODO  这里好像需要改一下  太麻烦了
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));

        }
        return modelAndView;
    }

    //2.查看商品详情,根据商品ID
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


    @GetMapping("/detailselect")
    public ModelAndView findByKeyWord(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        String keyWord = request.getParameter("keyWord");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("productList");
        //通过种类的级别 查询所有商品
        modelAndView.addObject("productList",productService.findByKeyWord(keyWord));
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVo());

        //获取用户 TODO  这里好像需要改一下  太麻烦了
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        }
        return modelAndView;
    }


    /**
     * 发布商品前的数据查找
     * @param session
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/beforepublish")
    public ModelAndView beforePublish(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("publish");
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVo());
        //查询所有的三级分类放在select标签中
        modelAndView.addObject("type_3",productService.getProductCategory3());
        User user = (User) session.getAttribute("user");
        if(user==null){
            modelAndView.addObject("findAllCartVoList",new ArrayList<>());
        }else{
            modelAndView.addObject("findAllCartVoList",cartService.findAllCartVoByUserId(user.getId()));
        }
        return modelAndView;
    }


    /**
     * 发布商品  开始保存
     * @param session
     * @param request
     * @param response
     * @param insert_product
     * @return
     */
    @PostMapping("/publish")
    public void publish(HttpSession session, HttpServletRequest request, HttpServletResponse response,Product insert_product) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        int categorylevelone_id = Integer.valueOf(request.getParameter("categorylevelone_id"));
        int categoryleveltwo_id = Integer.valueOf(request.getParameter("categoryleveltwo_id"));
        int categorylevelthree_id = Integer.valueOf(request.getParameter("categorylevelthree_id"));

        insert_product.setCategoryleveloneId(categorylevelone_id);
        insert_product.setCategoryleveltwoId(categoryleveltwo_id);
        insert_product.setCategorylevelthreeId(categorylevelthree_id);

        insert_product.setFileName("bk_3.jpg");

        log.info("准备保存的商品"+insert_product.toString());
        ResultInfo info=new ResultInfo();
        try{
            productService.save(insert_product);
            info.setCode(1);
            info.setMsg("保存成功");
        }catch (Exception ex){
            info.setCode(0);
            info.setMsg(ex.getMessage());
        }
        response.getWriter().write(JSON.toJSONString(info));
    }






}
