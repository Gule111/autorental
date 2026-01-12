package com.xzit.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.entity.User;
import com.xzit.entity.UserRole;
import com.xzit.mapper.UserMapper;
import com.xzit.mapper.UserRoleMapper;
import com.xzit.service.IOssService;
import com.xzit.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private final UserMapper userMapper;
    @Resource
    private IOssService ossService;
    @Resource
    private UserRoleMapper userRoleMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<String> getRoleName(int id) {
        return userMapper.selectRoleNameByUserId(id);
    }

    @Override
    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<User> searchByPage(Page<User> page, User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user.getDeptId() != null && user.getDeptId() == 0) {
            return userMapper.selectPage(page, null);
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(user.getDeptId()), "dept_id", user.getDeptId());
        queryWrapper.like(ObjectUtil.isNotEmpty(user.getRealname()), "realname", user.getRealname());
        queryWrapper.like(ObjectUtil.isNotEmpty(user.getNickname()), "nickname", user.getNickname());
        queryWrapper.like(ObjectUtil.isNotEmpty(user.getUsername()), "username", user.getUsername());
        queryWrapper.like(ObjectUtil.isNotEmpty(user.getPhone()), "phone", user.getPhone());
        queryWrapper.like(ObjectUtil.isNotEmpty(user.getEmail()), "email", user.getEmail());
        return baseMapper.selectPage(page, queryWrapper);

    }

    @Override
    public boolean delete(String ids) {
        String[] split = ids.split(",");
        List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
        try{
            if(!list.isEmpty()){
                list.forEach(id->{
                    User user = userMapper.selectById(id);
                    if(user.getIsAdmin()!=null && user.getIsAdmin()!=1){
//                        删除时候要删除用户头像
                        if(user.getAvatar()!=null){
                            ossService.delete(user.getAvatar());
                        }
                        baseMapper.deleteById(id);
                        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",id));
                    }
                });
            }
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean bindRole(Integer userId, List<Integer> list) {

//        先删除所有当前用户和角色的关联
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id",userId));

        if(list!=null){
            list.forEach(id->{
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(id);
                userRoleMapper.insert(userRole);
            });
            return  true;
        }
        return false;
    }
}
