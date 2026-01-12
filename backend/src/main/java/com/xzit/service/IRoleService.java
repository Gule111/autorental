package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IRoleService extends IService<Role> {

    Page selectList(Page<Role> page,Role  role);

    boolean hasUser(Integer id);

    boolean delete(String ids);

    boolean assignPermission(Integer roleId, List<Integer> permissionIds);

    List<Integer> getRoleIdByUserId(int id);
}
