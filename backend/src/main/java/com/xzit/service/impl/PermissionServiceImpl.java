package com.xzit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.Dept;
import com.xzit.entity.Permission;
import com.xzit.entity.User;
import com.xzit.mapper.PermissionMapper;
import com.xzit.mapper.UserMapper;
import com.xzit.service.IPermissionService;
import com.xzit.utils.RouteTreeUtils;
import com.xzit.vo.RolePermissionVo;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    private final UserMapper userMapper;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(UserMapper userMapper, PermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<Permission> selectPermissionByUserId(Integer userId) {
        return permissionMapper.selectPermissionByUserId(userId);
    }

    @Override
    public List<Permission> selectList() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        List<Permission> permissions = baseMapper.selectList(queryWrapper);
        return RouteTreeUtils.buildMenuTree(permissions, 0);
    }

    @Override
    public List<Permission> selectTree() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        queryWrapper.ne("permission_type", 2);
        List<Permission> permissions = baseMapper.selectList(queryWrapper);
        Permission root = new Permission();
        root.setId(0).setPid(-1);
        root.setPermissionLabel("根目录");
        permissions.add(root);
        return RouteTreeUtils.buildMenuTree(permissions, -1);
    }

    @Override
    public boolean hasChildren(Integer id) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
//        说明查到了数据
        return baseMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public RolePermissionVo selectPermissionTree(Integer userId, Integer roleId) {
        User user = userMapper.selectById(userId);
        List<Permission> list = null;
        // 判断用户是否为管理员，管理员查询所有权限，非管理员查询个人权限
        if (user != null && user.getIsAdmin() == 1) {
            list = baseMapper.selectList(null);
        } else {
            list = baseMapper.selectPermissionByUserId(userId);
        }
        // 将权限列表构建为树形结构
        List<Permission> permissionList = RouteTreeUtils.buildMenuTree(list, 0);
        // 查询角色已有的权限
        List<Permission> rolePermissionList = baseMapper.selectPermissionByRoleId(roleId);
        // 找出用户权限中与角色权限的交集
        List<Permission> newList=new ArrayList<>(list);
        List<Integer> ids= new ArrayList<>(list.stream().map(Permission::getId).toList());
        List<Integer> ids1=rolePermissionList.stream().map(Permission::getId).toList();
        ids.retainAll(ids1);

        Object[] array = ids.toArray();
        // 构建角色权限视图对象并返回
        RolePermissionVo rolePermissionVo=new RolePermissionVo();
        rolePermissionVo.setCheckedList(array).setPermissionList(permissionList);
        return rolePermissionVo;

    }
}
