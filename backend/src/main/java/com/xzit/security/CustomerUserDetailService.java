package com.xzit.security;

import com.xzit.entity.Permission;
import com.xzit.entity.User;
import com.xzit.service.IPermissionService;
import com.xzit.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author 31507
 */
/**
 * 实现Spring Security中的UserDetailsService接口，用于根据用户名查询用户信息
 * 主要作用是将自定义的用户查询逻辑与Spring Security集成，以支持用户认证和授权
 */
@Component
public class CustomerUserDetailService implements UserDetailsService {

    /**
     * 注入用户服务接口，用于实际执行用户查询操作
     * 通过@Resource注解自动装配，解耦组件依赖
     */
    @Resource
    private IUserService userService;
    @Resource
    private IPermissionService  permissionService;

    /**
     * 根据用户名加载用户信息
     * 此方法是UserDetailsService接口的核心实现，负责根据传入的用户名查询并返回用户详情
     * 如果查询不到用户，则抛出UsernameNotFoundException异常，告知Spring Security用户不存在
     *
     * @param username 用户名，查询用户信息的唯一标识
     * @return UserDetails 实现类，表示查询到的用户详情
     * @throws UsernameNotFoundException 如果查询不到用户信息，抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
//        查询用户权限列表
        List<Permission> permissions = permissionService.selectPermissionByUserId(user.getId());
        user.setPermissionList(permissions);
        //通过stream流处理，将权限对象转换为权限字符串列表
        List<String> list = permissions.stream().filter(Objects::nonNull)
                .map(Permission::getPermissionCode)//获取权限字符串列表
                .filter(Objects::nonNull)
                .toList();
        String[] array = list.toArray(new String[list.size()]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(array);
        user.setAuthorities(authorityList);
        return user;
    }
}

