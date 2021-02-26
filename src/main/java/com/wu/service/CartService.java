package com.wu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wu.entity.Cart;
import com.wu.entity.User;
import com.wu.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
public interface CartService extends IService<Cart> {


    //这是购物车的封装vo
    List<CartVO> findAllCartVoByUserId(Integer id);


}
