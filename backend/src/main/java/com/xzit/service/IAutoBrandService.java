package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.AutoBrand;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IAutoBrandService extends IService<AutoBrand> {
    Page<AutoBrand> searchByPage(Page<AutoBrand> page, AutoBrand autoBrand);
    List<AutoBrand> selectByMakerId(Integer makerId);
}
