package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.entity.Permission;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectPermissionByUserId(Integer userId);
    List<Permission> selectPermissionByRoleId(Integer roleId);
}
