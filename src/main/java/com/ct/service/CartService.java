package com.ct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ct.entity.Cart;
import com.ct.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈腾
 * @since 2021-2-19
 */
public interface CartService extends IService<Cart> {


    //这是购物车的封装vo
    List<CartVO> findAllCartVoByUserId(Integer id);


}
