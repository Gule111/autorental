package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.Order;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IOrderService extends IService<Order> {

    boolean insert(Order order);
    Page<Order> searchUnfinished(Page<Order> page, Order order);

    boolean returnAuto(Order order,Integer maintainMileage);

    Page<Order> search(Page<Order> page, Order order);

    long countViolation(Integer autoId);

}
