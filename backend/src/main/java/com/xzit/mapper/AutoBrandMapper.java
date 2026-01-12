package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.AutoBrand;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface AutoBrandMapper extends BaseMapper<AutoBrand> {

    Page<AutoBrand> searchByPage(@Param("page") Page<AutoBrand> page, @Param("autoBrand") AutoBrand autoBrand);
}
