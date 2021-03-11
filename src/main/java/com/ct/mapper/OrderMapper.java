package com.ct.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ct.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈腾
 * @since 2020-12-29
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {


}
