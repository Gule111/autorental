package com.xzit.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.Role;
import com.xzit.entity.RolePermission;
import com.xzit.entity.User;
import com.xzit.entity.UserRole;
import com.xzit.mapper.RoleMapper;
import com.xzit.mapper.RolePermissionMapper;
import com.xzit.mapper.UserMapper;
import com.xzit.mapper.UserRoleMapper;
import com.xzit.service.IRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public Page selectList(Page<Role> page, Role role) {
        // 创建一个QueryWrapper对象用于封装查询条件
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();

// 如果角色名称不为空，则添加模糊查询条件
        queryWrapper.like(StrUtil.isNotEmpty(role.getRoleName()), "role_name", role.getRoleName());
// 添加按照创建时间的升序排序条件
        queryWrapper.orderByAsc("create_time");

// 获取当前角色的创建者ID
        Integer userId = role.getCreaterId();
// 根据创建者ID查询用户信息
        User user = userMapper.selectById(userId);

// 如果用户存在且不是管理员，则添加创建者ID的查询条件以限制查询结果
        if (user != null && user.getIsAdmin() != 1) {
// 1是管理员
            queryWrapper.eq("creater_id", userId);
        }

// 调用baseMapper的selectPage方法进行分页查询，并返回结果
        return baseMapper.selectPage(page, queryWrapper);

    }

    @Override
    public boolean hasUser(Integer id) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);
        return userRoleMapper.selectCount(queryWrapper) > 0;

    }

    @Override
    public boolean delete(String ids) {
        String[] split = ids.split(",");
        List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
        if(!list.isEmpty()){
            for (Integer id : list) {
                if(!hasUser(id)){
                    rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id",id)) ;
                    baseMapper.deleteById(id);
                }
            }
        }
        return false;
    }

    @Override
    public boolean assignPermission(Integer roleId, List<Integer> permissionIds) {
//        把原来数据删除
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        rolePermissionMapper.delete(queryWrapper);
//        插入新的数据
        if(permissionIds != null && !permissionIds.isEmpty()){
            for (Integer permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
        }
        return true;
    }

    @Override
    public List<Integer> getRoleIdByUserId(int id) {

        return baseMapper.selectRoleIdByUserId(id) ;
    }
}
