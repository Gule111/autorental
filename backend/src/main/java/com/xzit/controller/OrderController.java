package com.xzit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.Order;
import com.xzit.service.IOrderService;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@RestController
@RequestMapping("/auto/order")
public class OrderController {
    @Resource
    private IOrderService orderService;
    @PostMapping
    public Result save(@RequestBody Order order){
        return orderService.insert(order)?Result.success():Result.fail();
    }
    @Value("${auto.info.maintain-mileage}")
    private Integer maintainMileage;


    /**
     * 查询未归还车辆的订单
     */
    @PostMapping("/unfinished/{start}/{size}")
    public Result searchUnfinished(@PathVariable int start,@PathVariable int size,
                                   @RequestBody Order order){
        Page<Order> page = new Page<>(start,size);
        return Result.success(orderService.searchUnfinished(page,order));
    }
    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start,@PathVariable int size,@RequestBody Order order){
        Page<Order> page = new Page<>(start,size);
        return Result.success(orderService.search(page,order));
    }

    @PutMapping
    public Result update(@RequestBody Order order){
        return orderService.returnAuto(order,maintainMileage)?Result.success():Result.fail();
    }

    @GetMapping("/countViolation/{autoId}")
    public Result countViolation(@PathVariable Integer autoId){
        long count=orderService.countViolation(autoId);
        return Result.success(count);
    }
    @GetMapping("/doReturnDeposit/{orderId}")
    public Result doReturnDeposit(@PathVariable Integer orderId){
        Order order = orderService.getById(orderId);
        order.setDepositReturn(1);
        return orderService.updateById(order)?Result.success():Result.fail();
    }

}
