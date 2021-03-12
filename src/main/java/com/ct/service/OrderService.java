package com.ct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ct.entity.Order;
import com.ct.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈腾
 * @since 2021-2-19
 */
public interface OrderService extends IService<Order> {


    boolean save(Order order, User user, String selectAddress, String newAddress);
}
