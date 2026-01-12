package com.xzit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.Customer;
import com.xzit.service.ICustomerService;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@RestController
@RequestMapping("/auto/customer")
public class CustomerController {
    @Resource
    private ICustomerService customerService;

    @PostMapping
    public Result save(@RequestBody Customer customer) {
        return customerService.save(customer) ? Result.success(customer.getId()) : Result.fail();
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        return customerService.delete(ids) ? Result.success() : Result.fail();
    }

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size, @RequestBody Customer customer) {
        Page<Customer> page = new Page<>(start, size);
        return Result.success().setData(customerService.searchByPage(page, customer));
    }
    @PostMapping("/selectCustomerByTel")
    public Result selectCustomerByTel(@RequestBody Customer customer){
        return Result.success().setData(customerService.selectCustomerByTel(customer));
    }
}
