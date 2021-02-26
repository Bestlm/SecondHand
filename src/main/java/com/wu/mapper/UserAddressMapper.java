package com.wu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wu.entity.User;
import com.wu.entity.UserAddress;
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
public interface UserAddressMapper extends BaseMapper<UserAddress> {


}
