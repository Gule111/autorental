package com.xzit.utils;

import com.xzit.entity.Permission;
import com.xzit.vo.RouteVo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 31507
 */

public class RouteTreeUtils {
    /**
     * 基于权限列表和父权限ID构建路由树。
     *
     * @param permissionList 权限列表，包含所有路由的权限信息。
     * @param pid            父权限ID，用于筛选特定父级下的路由。
     * @return 返回一个路由树的列表，每个路由包含路径、名称、组件等信息，以及可能的子路由。
     */
  public static List<RouteVo> buildRouteTree(List<Permission> permissionList, int pid) {
    List<RouteVo> routeVOList = new ArrayList<>();

    // 筛选出父ID为pid的权限
    permissionList.stream()
        .filter(permission -> permission != null && permission.getPid() == pid)
        .forEach(permission -> {
            // 创建路由对象
            RouteVo routeVO = new RouteVo();

            // 1 设置路由基本信息
            routeVO.setPath(permission.getRoutePath());      // 路由路径: "/system"
            routeVO.setName(permission.getRouteName());      // 路由名称: "System"

            // 2 判断是否为根菜单
            if (permission.getPid() == 0) {
                // 根菜单使用Layout布局
                routeVO.setComponent("Layout");
                routeVO.setAlwaysShow(true);
            } else {
                // 子菜单使用具体组件路径
                routeVO.setComponent(permission.getRouteUrl());  // "system/user/index"
                routeVO.setAlwaysShow(false);
            }

            // 3 设置元信息(meta)
            routeVO.setMeta(routeVO.new Meta(
                permission.getPermissionLabel(),     // 菜单标题: "用户管理"
                permission.getIcon(),                // 图标: "user"
                permission.getPermissionCode().split(",")  // 权限代码: ["user:list"]
            ));

            // 4 递归构建子菜单
            List<RouteVo> children = buildRouteTree(permissionList, permission.getId());
            routeVO.setChildren(children);

            routeVOList.add(routeVO);
        });

    return routeVOList;
}


/*
   分配权限
 * 构建菜单
 * list是该用户下传入的所有权限列表，然后通过递归形成一个树的结构
 */
    public static List<Permission> buildMenuTree(List<Permission> list, int pid) {
        List<Permission> menuList = new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream()
                .filter(permission -> permission != null
                        && permission.getPid() == pid)
                .forEach(permission -> {
                    Permission menu = new Permission();
                    BeanUtils.copyProperties(permission, menu);
                    menu.setChildren(buildMenuTree(list, permission.getId()));
                    menuList.add(menu);
                });
        return menuList;
    }

}
