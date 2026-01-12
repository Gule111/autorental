package com.xzit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.RentalType;
import com.xzit.mapper.RentalTypeMapper;
import com.xzit.service.IRentalTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@Service
public class RentalTypeServiceImpl extends ServiceImpl<RentalTypeMapper, RentalType> implements IRentalTypeService {

    @Override
    public Page selectPage(Page page, RentalType rentalType) {
        QueryWrapper<RentalType> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(rentalType.getTypeName()), "type_name", rentalType.getTypeName());
        queryWrapper.le(rentalType.getTypeDiscount() != null, "type_discount", rentalType.getHighDiscount());
        queryWrapper.ge(rentalType.getTypeDiscount() != null, "type_discount", rentalType.getLowDiscount());
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean delete(String ids) {
        List<String> split = StrUtil.split(ids, ",");
        List<Integer> list = split.stream().map(Integer::parseInt).toList();
        if (!list.isEmpty()) {
            return baseMapper.deleteBatchIds(list)>0;
        }
        return false;

    }
}
