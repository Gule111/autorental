package com.xzit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.AutoInfo;
import com.xzit.entity.Order;
import com.xzit.mapper.AutoInfoMapper;
import com.xzit.mapper.OrderMapper;
import com.xzit.service.IOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final AutoInfoMapper autoInfoMapper;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(AutoInfoMapper autoInfoMapper, OrderMapper orderMapper) {
        this.autoInfoMapper = autoInfoMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public boolean insert(Order order) {
        Integer autoId = order.getAutoId();
        AutoInfo autoInfo = autoInfoMapper.selectById(autoId);
        autoInfo.setStatus(1);
        autoInfoMapper.updateById(autoInfo);
        String number= DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        order.setOrderNum(number);
        order.setRentalTime(LocalDateTime.now());
        return orderMapper.insert( order)>0;
    }
    @Override
    public Page<Order> searchUnfinished(Page<Order> page, Order order) {
        return baseMapper.searchUnfinished(page,order);
    }

    @Override
    public boolean returnAuto(Order order,Integer maintainMileage) {
        //修改车辆状态
        try {
            Integer autoId = order.getAutoId();
            AutoInfo autoInfo = autoInfoMapper.selectById(autoId);
            autoInfo.setStatus(0);
            //增加车辆行驶里程
            autoInfo.setMileage(order.getReturnMileage());
            //修改应保次数
            autoInfo.setExpectedNum(autoInfo.getMileage() / maintainMileage);
            autoInfoMapper.updateById(autoInfo);
            //update更新订单
            baseMapper.updateById(order);
            return true;
        }catch (Exception e){
            throw new RuntimeException("归还失败");
        }
    }

    @Override
    public Page<Order> search(Page<Order> page, Order order) {
        return baseMapper.search(page,order);
    }

    @Override
    public long countViolation(Integer autoId) {
        return baseMapper.countViolation(autoId);
    }

}
