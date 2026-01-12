package com.xzit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.Permission;
import com.xzit.vo.RolePermissionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
public interface IPermissionService extends IService<Permission> {
    List<Permission> selectPermissionByUserId(Integer userId);
    List<Permission> selectList();
    List<Permission> selectTree();
    boolean hasChildren(Integer id);
    RolePermissionVo selectPermissionTree(Integer userId,Integer roleId);

}
