package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.AutoInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IAutoInfoService extends IService<AutoInfo> {


    Page<AutoInfo> searchByPage(Page<AutoInfo> page, AutoInfo autoInfo);

    boolean delete(String ids);


    AutoInfo selectByAutoNum(String autoNum);

    List<AutoInfo> toBeMaintain();

}
