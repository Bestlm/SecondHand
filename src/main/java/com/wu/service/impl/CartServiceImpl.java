package com.wu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wu.entity.Cart;
import com.wu.entity.Product;
import com.wu.entity.User;
import com.wu.enums.ResultEnum;
import com.wu.exception.MallException;
import com.wu.mapper.CartMapper;
import com.wu.mapper.ProductMapper;
import com.wu.mapper.UserMapper;
import com.wu.service.CartService;
import com.wu.service.UserService;
import com.wu.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    //这是方法重写,主要是当我们下单之后,在订单存入数据库中,还要进行减库存操作,原始的save方法并没有这个逻辑,所以重写
    @Override
    public boolean save(Cart entity) {
        //减库存
        Product product = productMapper.selectById(entity.getProductId());
        Integer stock=product.getStock()-entity.getQuantity();
        //这里要对库存是否异常进行检查,抛出异常,建立异常处理类,还需要个枚举,列举所有异常
        if (stock<0){
            log.error("[添加购物车]库存不足!stock={}",stock);
            throw new MallException(ResultEnum.STOCK_ERROR);

        }
        productMapper.updateById(product.setStock(stock));
        if (cartMapper.insert(entity)==1){
            return true;
        }
        return false;
    }

    @Override
    public List<CartVO> findAllCartVoByUserId(Integer id) {
        List<CartVO> cartVOList = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",id);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        for (Cart cart : cartList) {
            CartVO cartVO = new CartVO();
            Product product = productMapper.selectById(cart.getProductId());
            BeanUtils.copyProperties(product,cartVO);

            BeanUtils.copyProperties(cart,cartVO);
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }
}
