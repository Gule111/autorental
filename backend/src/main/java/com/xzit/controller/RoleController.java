package com.xzit.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.Role;
import com.xzit.entity.User;
import com.xzit.service.IPermissionService;
import com.xzit.service.IRoleService;
import com.xzit.service.IUserService;
import com.xzit.utils.JWTUtils;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@RestController
@RequestMapping("/auto/role")
public class RoleController {
    @Resource
    private IRoleService roleService;
    @Resource
    private IPermissionService permissionService;

    @Resource
    private IUserService userService;

    @PostMapping("{start}/{size}")
    public Result search(@PathVariable int start
            , @PathVariable int size
            , @RequestBody Role role) {
//        从SpringSecurity中获取当前用户信息
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user=(User) authentication.getPrincipal();
//        role.setCreaterId(user.getId());

//        通过token获取当前用户信息
//        String token = request.getHeader("token");
//        if(StrUtil.isEmpty( token)){
//            token = request.getParameter("token");
//        }
//        Object claim = JWTUtils.parseToken(token).getClaim("userid");
//        String userId=claim.toString();
        Page<Role> page = new Page<>(start, size);
        Page page1 = roleService.selectList(page, role);
        return Result.success().setData(roleService.selectList(page, role));
    }
    @PreAuthorize("hasAuthority('sys:role:add')")
    @PostMapping
    public Result save(@RequestBody Role role) {
        return roleService.save(role) ? Result.success() : Result.fail();
    }

    @PutMapping
    public Result update(@RequestBody Role role) {
        return roleService.updateById(role) ? Result.success() : Result.fail();
    }

    @GetMapping("hasUser/{id}")
    public Result hasUser(@PathVariable Integer id) {
        return roleService.hasUser(id) ? Result.success().setMessage("有") : Result.success().setMessage("无");
    }

    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        return roleService.delete(ids) ? Result.success().setMessage("有") : Result.success().setMessage("无");
    }
//    获取当前角色id，树结构，用户id

    /**
     * 获取权限树
     * <p>
     * 根据用户ID和角色ID获取对应的权限树结构
     *
     * @param userId 用户ID，用于获取用户相关的权限信息
     * @param roleId 角色ID，用于获取角色相关的权限信息
     * @return 返回包含权限树信息的Result对象
     */
    @GetMapping("/permissionTree")
    public Result permissionTree(Integer userId, Integer roleId) {
        return Result.success().setData(permissionService.selectPermissionTree(userId, roleId));
    }

    /**
     * 分配权限给角色
     * <p>
     * 根据角色ID和权限ID列表，为角色分配相应的权限
     *
     * @param roleId        角色ID，标识要分配权限的角色
     * @param permissionIds 权限ID列表，以字符串形式传入，内部以逗号分隔
     * @return 如果权限分配成功，则返回成功结果；否则返回失败结果
     */
    @GetMapping("/{roleId}/{permissionIds}")
    public Result assignPermission(@PathVariable Integer roleId, @PathVariable String permissionIds) {
        // 将权限ID字符串转换为Integer列表
        List<Integer> list = Arrays.stream(permissionIds.split(",")).map(Integer::parseInt).toList();

        // 根据角色ID和权限ID列表分配权限，并根据分配结果返回相应的结果
        return roleService.assignPermission(roleId, list) ? Result.success() : Result.fail();
    }
//    获取所有角色不分页
    @GetMapping
    public Result list(){
        return Result.success().setData(roleService.list());
    }


}
