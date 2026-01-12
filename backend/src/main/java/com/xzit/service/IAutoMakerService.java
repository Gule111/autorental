package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.AutoMaker;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IAutoMakerService extends IService<AutoMaker> {

    Page<AutoMaker> search(int start, int size, AutoMaker autoMaker);
}
