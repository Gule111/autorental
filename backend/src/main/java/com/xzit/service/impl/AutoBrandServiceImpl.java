package com.xzit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.AutoBrand;
import com.xzit.mapper.AutoBrandMapper;
import com.xzit.service.IAutoBrandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@Service
public class AutoBrandServiceImpl extends ServiceImpl<AutoBrandMapper, AutoBrand> implements IAutoBrandService {
    @Resource
    private AutoBrandMapper autoBrandMapper;
    @Override
    public Page<AutoBrand> searchByPage(Page<AutoBrand> page, AutoBrand autoBrand) {
        return autoBrandMapper.searchByPage(page, autoBrand);
    }

    @Override
    public List<AutoBrand> selectByMakerId(Integer makerId) {
        QueryWrapper<AutoBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mid",makerId);
        return baseMapper.selectList(queryWrapper);
    }
}
