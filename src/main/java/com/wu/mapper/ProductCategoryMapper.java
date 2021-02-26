package com.wu.mapper;

import com.wu.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wu.vo.ProductCategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {


}
