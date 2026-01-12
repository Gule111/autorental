package com.xzit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.AutoMaker;
import com.xzit.mapper.AutoMakerMapper;
import com.xzit.service.IAutoMakerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@Service
public class AutoMakerServiceImpl extends ServiceImpl<AutoMakerMapper, AutoMaker> implements IAutoMakerService {

    @Override
    public Page<AutoMaker> search(int start, int size, AutoMaker autoMaker) {
        // 创建一个QueryWrapper对象用于后续的查询
        QueryWrapper<AutoMaker> queryWrapper = new QueryWrapper<>();
// 设置查询结果按照'order_letter'字段升序排序
        queryWrapper.orderByAsc("order_letter")
// 根据名称进行模糊查询，如果autoMaker.getName()不为空则执行此查询条件
                .like(StrUtil.isNotEmpty(autoMaker.getName())
                        , "name"
                        , autoMaker.getName());

// 创建一个Page对象用于分页查询，传入起始页码和每页大小
        Page<AutoMaker> page = new Page<>(start, size);
// 执行分页查询，传入page和queryWrapper对象
        this.page(page, queryWrapper);

// 返回查询结果的page对象
        return page;

    }
}
