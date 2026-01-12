package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.Customer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface ICustomerService extends IService<Customer> {

    boolean delete(String ids);

    Page searchByPage(Page<Customer> page, Customer customer);

    Customer selectCustomerByTel(Customer customer);
}
