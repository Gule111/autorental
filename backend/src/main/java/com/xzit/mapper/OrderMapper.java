package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface OrderMapper extends BaseMapper<Order> {
    Page<Order> searchUnfinished(@Param("page") Page<Order> page, @Param("order") Order order);

    Page<Order> search(@Param("page")Page<Order> page, @Param("order") Order order);

    long countViolation(Integer autoId);
}
