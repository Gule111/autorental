package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select role_id from sys_user_role where user_id=#{userId}")
    List<Integer> selectRoleIdByUserId(int userId);
}
