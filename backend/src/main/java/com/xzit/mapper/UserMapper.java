package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface UserMapper extends BaseMapper<User> {
    List<String> selectRoleNameByUserId(Integer userId);
}
