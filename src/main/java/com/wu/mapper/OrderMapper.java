package com.wu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wu.entity.Order;
import com.wu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wustudy
 * @since 2020-12-29
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {


}
