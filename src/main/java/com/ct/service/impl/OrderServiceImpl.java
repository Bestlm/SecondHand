package com.ct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ct.entity.*;
import com.ct.mapper.*;
import com.ct.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈腾
 * @since 2020-12-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public boolean save(Order order, User user, String address, String remark) {

        if (order.getUserAddress().equals("newAddress")){
            UserAddress userAddress  = new UserAddress();

            //先把老地址默认值改了
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("isdefault",1);
            UserAddress oldAdress = userAddressMapper.selectOne(queryWrapper);
            oldAdress.setIsdefault(0);
            userAddressMapper.updateById(oldAdress);

            //对新地址进行赋值
            userAddress.setAddress(address).setRemark(remark).setUserId(user.getId()).setIsdefault(1);
            //把下单操作的地址改成新地址
            order.setUserAddress(address);

            //将新地址加入地址数据库中
            userAddressMapper.insert(userAddress);
        }

        order.setUserId(user.getId()).setLoginName(user.getLoginName());
        //这一步是获取订单编号  订单编号  也可以自动生成  比较方便
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for(int i=0;i<32;i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber =  result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.setSerialnumber(seriaNumber);
        orderMapper.insert(order);


        //当下单完成后，存入数据库order,我们还需要将下单表所包含的商品加入到真正的下单数据库order_detail中。更方便查看
        //此时 我们有了user就可以取出cart中的所有商品信息，还差一个oreder的id信息仅此而已，当然了我们有个笨方法就是查数据库
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        List<Cart> cartlist = cartMapper.selectList(queryWrapper);
        for (Cart cart : cartlist) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setId(null);
            orderDetail.setOrderId(order.getId());
            orderDetailMapper.insert(orderDetail);
        }

        //所有完成后，就开始清除你这个用户的购物车了
        queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        cartMapper.delete(queryWrapper);
        return true;
    }
}
