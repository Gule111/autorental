package com.xzit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.Dept;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IDeptService extends IService<Dept> {
    List<Dept> selectList(Dept dept);

    List<Dept> selectTree();


    boolean hasChildren(Integer id);
}
