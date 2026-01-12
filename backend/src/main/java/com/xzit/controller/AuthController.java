package com.xzit.controller;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import com.xzit.entity.Permission;
import com.xzit.entity.User;
import com.xzit.service.IUserService;
import com.xzit.utils.JWTUtils;
import com.xzit.utils.RedisUtils;
import com.xzit.utils.Result;
import com.xzit.utils.RouteTreeUtils;
import com.xzit.vo.RouteVo;
import com.xzit.vo.TokenVo;
import com.xzit.vo.UserInfoVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 31507
 */
@RestController
@RequestMapping("/auto/auth/")
public class AuthController {
    private final RedisUtils redisUtils;
    @Resource
    private IUserService userService;

    public AuthController(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    /**
     * 刷新Token
     * <p>
     * 此方法用于验证和刷新用户的身份验证Token它首先尝试从HTTP请求的头部获取现有的Token，
     * 如果头部没有Token，则尝试从请求参数中获取如果Token有效，并且Token中的用户名与当前
     * 认证的用户信息匹配，则生成一个新的Token，并将其有效期存储在Redis中最后，将新Token的信息
     * 返回给客户端
     *
     * @param request HTTP请求对象，用于获取请求头部或参数中的Token
     * @return 返回一个包含新Token信息的结果对象
     */
    @PostMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request) {

        // 尝试从请求头部获取Token
        String token = request.getHeader("token");
        // 如果头部没有Token，尝试从请求参数中获取
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter("token");
        }
        // 获取当前的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取当前认证用户的信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 解析Token以获取用户名
        String string = JWTUtils.parseToken(token).getClaim("username").toString();
        // 初始化新Token为空
        String newToken = "";
        // 如果Token中的用户名与当前用户信息匹配，则生成新的Token
        if (string.equals(userDetails.getUsername())) {
            Map<String, Object> map = new HashMap<>() {{
                put("username", userDetails.getUsername());
            }};
            // 使用用户信息创建JWT token
            newToken = JWTUtils.createToken(map);
        }
        // 获取新Token的过期时间
        NumberWithFormat claim = (NumberWithFormat) JWTUtils.parseToken(newToken).getClaim(JWTPayload.EXPIRES_AT);
        DateTime dateTime = (DateTime) claim.convert(DateTime.class, claim);
        long expireTime = dateTime.getTime();
        // 创建Token信息对象以存储过期时间
        TokenVo tokenVo = new TokenVo();
        tokenVo.setExpireTime(expireTime);
        tokenVo.setToken(newToken);
        // 删除旧的Token
        redisUtils.delete("token:" + token);
        // 获取当前时间
        long nowTime = DateTime.now().getTime();
        // 生成新的Token键
        String newTokenKey = "token:" + newToken;
        // 将新的Token存储到Redis中，并设置其过期时间
        redisUtils.set(newTokenKey, newToken, (expireTime - nowTime) / 1000);
        // 返回成功结果和新Token信息
        return Result.success(tokenVo).setMessage("刷新成功");
    }

    /**
 * 获取用户信息
 * 该方法通过Spring Security框架获取当前登录用户的信息，并返回用户信息对象
 * 如果用户未登录，则返回失败结果
 *
 * @return Result对象，包含UserInfoVo用户信息对象
 */
@GetMapping("/getInfo")
public Result getUserInfo() {
    //从securityContextHolder获取
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        return Result.fail().setMessage("用户未登录");
    }
    User user = (User) authentication.getPrincipal();
//    List<String> roleName = userService.getRoleName(user.getId
    List<Permission> permissionList = user.getPermissionList();
    Object[] array = permissionList.stream().filter(Objects::nonNull).map(Permission::getPermissionCode).toArray();
//        Object[] array = user.getPermissionList().stream().filter(Objects::nonNull).map(Permission::getPermissionCode).toArray();
    // 创建UserInfoVo对象，用于存储用户信息
    UserInfoVo userInfoVo = new UserInfoVo(user.getId(), user.getUsername(), user.getAvatar(), user.getNickname(), array);
    // 返回成功结果，包含用户信息对象
    return Result.success(userInfoVo).setMessage("获取用户信息成功");
}

@GetMapping("/menuList")
public Result getMenuList(){
    // 1 获取当前登录用户信息
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
        return Result.fail().setMessage("用户未登录");
    }

    // 2 获取用户对象(包含权限列表)
    User user = (User) authentication.getPrincipal();

    // 3 获取用户的所有权限列表
    List<Permission> permissionList = user.getPermissionList();
    // permissionList包含:
    // - 菜单权限(permission_type = 0 或 1)
    // - 按钮权限(permission_type = 2)

    // 4 过滤掉按钮权限,只保留菜单
    permissionList.removeIf(permission -> permission.getPermissionType() == 2);

    // 5 构建菜单树
    List<RouteVo> routeVos = RouteTreeUtils.buildRouteTree(permissionList, 0);

    // 6 返回给前端
    return Result.success(routeVos).setMessage("获取表单列表成功");
}

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response){
        String token=request.getHeader("token");
        if (StrUtil.isEmpty(token)){
            token=request.getParameter("token");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            //用户一旦登出系统，则清除redis中的token
            redisUtils.delete("token:"+token);
            SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
            handler.logout(request,response,authentication);
            return Result.success().setMessage("登出成功");
        }
        return Result.fail().setMessage("登出失败");
    }

}
