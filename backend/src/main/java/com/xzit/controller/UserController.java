package com.xzit.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.User;
import com.xzit.service.IRoleService;
import com.xzit.service.IUserService;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@RestController
@RequestMapping("/auto/user")
public class UserController {
    @Resource
    private IRoleService roleService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IUserService userService;
    @GetMapping
    public Result<List<User>> list(){
        return Result.success(userService.list());
    }
    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size,@RequestBody User user){
        return Result.success(userService.searchByPage(new Page<>(start,size),user));
    }
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        User user1 = userService.selectByUsername(user.getUsername());
        if(user1!=null){
            return Result.fail().setMessage("用户已存在");
        }else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setIsAdmin(0);
            if(StrUtil.isEmpty(user.getAvatar())){
                user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            }
            return userService.save(user)?Result.success().setMessage("添加用户成功"):Result.fail().setMessage("添加用户失败");
        }
    }
    @PutMapping
    public Result update(@RequestBody User user){
        User user1 = userService.selectByUsername(user.getUsername());
        if(user1!=null&&!user1.getId().equals(user.getId())){
            return Result.fail().setMessage("用户已存在");
        }else{
            if(StrUtil.isEmpty(user.getAvatar())){
                user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            }
        }
        return userService.updateById(user)?Result.success().setMessage("修改用户成功"):Result.fail().setMessage("修改用户失败");
    }
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        return userService.delete(ids)?Result.success().setMessage("删除用户成功"):Result.fail().setMessage("删除用户失败");
    }
//        获取用户所绑定的角色id
    @GetMapping("/role/{id}")
    public Result selectRoleByUserId(@PathVariable int id){
        return Result.success(roleService.getRoleIdByUserId(id));
    }
    @GetMapping("/bind/{userId}/{roleIds}")
    public Result bindRole(@PathVariable Integer userId, @PathVariable String roleIds) {
        // 将角色ID字符串转换为Integer列表
        List<Integer> list = Arrays.stream(roleIds.split(",")).map(Integer::parseInt).toList();

        // 根据用户ID和角色ID列表绑定角色，并根据绑定结果返回相应的结果
        return userService.bindRole(userId, list) ? Result.success() : Result.fail();
    }
}
