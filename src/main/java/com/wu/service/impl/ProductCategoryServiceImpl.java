package com.wu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wu.entity.Product;
import com.wu.entity.ProductCategory;
import com.wu.mapper.ProductCategoryMapper;
import com.wu.mapper.ProductMapper;
import com.wu.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.service.ProductService;
import com.wu.vo.ProductCategoryVo;
import com.wu.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * TODO 其实这里不应该实现这个接口  应该实现MyBatis中的ServiceImpl<实体的Mapper，实体>
 */
@Service
public class ProductCategoryServiceImpl  implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;


    /**
     * 获取所有的种类VO
     * @return
     */
    @Override
    public List<ProductCategoryVo> getAllProductCategoryVo() {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",1);
        //查询所有一级分类
        List<ProductCategory> levelOne = productCategoryMapper.selectList(queryWrapper);
        //把所有一级分类的ID和Name取出来 封装到VO对象里
        List<ProductCategoryVo> levelOneVo = levelOne.stream().map(e -> new ProductCategoryVo(e.getId(),e.getName())).collect(Collectors.toList());

        // 完善VO对象
        for (int i = 0; i <levelOneVo.size() ; i++) {
            //对于一级类型 BannerImg是首页分类顶部的图片
            levelOneVo.get(i).setBannerImg("/images/banner"+i+".png");
            //对于一级类型 BannerImg是首页分类左边的图片
            levelOneVo.get(i).setTopImg("/images/top"+i+".png");

            //查询所属类别1的所有商品VO，并存放在类别1的VO中
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("categorylevelone_id",levelOneVo.get(i).getId());
            List<Product> Productlist = productMapper.selectList(queryWrapper);
            //查询所有商品的VO对象
            List<ProductVO> productVOList = Productlist.stream().map(
                    e ->new ProductVO(
                            e.getId(),
                            e.getName(),
                            e.getPrice(),
                            "/images/"+e.getFileName()
                    )
            ).collect(Collectors.toList());
            levelOneVo.get(i).setProductVOList(productVOList);
        }

        //遍历讲二级分类封装进去
        for (ProductCategoryVo levelOneproductCategoryVo : levelOneVo) {
            queryWrapper = new QueryWrapper();
            //得到所属类别1的类别2的VO对象
            queryWrapper.eq("type",2);
            queryWrapper.eq("parent_id",levelOneproductCategoryVo.getId());
            List<ProductCategory> levelTwo = productCategoryMapper.selectList(queryWrapper);
            List<ProductCategoryVo> levelTwoVo = levelTwo.stream().map(e -> new ProductCategoryVo(e.getId(),e.getName())).collect(Collectors.toList());

            //TODO 其实应该把所属种类2的商品查出来 存放在levelTwoVo中  种类三同理
            //把种类2封装到种类1级中
            levelOneproductCategoryVo.setChildren(levelTwoVo);
            //再循环遍历种类3
            for (ProductCategoryVo levelTwoproductCategoryTwoVo : levelTwoVo) {
                queryWrapper = new QueryWrapper();
                //得到三级分类
                queryWrapper.eq("type",3);
                queryWrapper.eq("parent_id",levelTwoproductCategoryTwoVo.getId());
                List<ProductCategory> levelThree = productCategoryMapper.selectList(queryWrapper);
                List<ProductCategoryVo> levelThreeVo = levelThree.stream().map(e -> new ProductCategoryVo(e.getId(),e.getName())).collect(Collectors.toList());
                levelTwoproductCategoryTwoVo.setChildren(levelThreeVo);
            }
        }
        return levelOneVo;
    }

    /**
     * 通过parentId的值查找到对应的所有的子分类
     * @param parentId
     * @return
     */
    @Override
    public List<ProductCategory> selectProductCategoryByParentId(int parentId) {
    QueryWrapper<ProductCategory> queryWrapper=new QueryWrapper<ProductCategory>();
    queryWrapper.eq("parent_id",parentId);
    List<ProductCategory> categories = productCategoryMapper.selectList(queryWrapper);
    return categories;
    }

    @Override
    public List<ProductCategory> SelectProductCategoryListOne() {
        QueryWrapper<ProductCategory> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",1);
        List<ProductCategory> productCategories = productCategoryMapper.selectList(queryWrapper);
        return productCategories;
    }

    @Override
    public List<ProductCategory> SelectProductCategoryListThree() {
        QueryWrapper<ProductCategory> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",3);
        List<ProductCategory> productCategories = productCategoryMapper.selectList(queryWrapper);
        return productCategories;
    }

    @Override
    public List<ProductCategory> SelectProductCategoryListTwo() {
        QueryWrapper<ProductCategory> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",2);
        List<ProductCategory> productCategories = productCategoryMapper.selectList(queryWrapper);
        return productCategories;
    }
}
