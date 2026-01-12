package com.xzit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzit.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */

public interface IUserService extends IService<User> {

    List<String> getRoleName(int id);

    User selectByUsername(String username);

    Page<User> searchByPage(Page<User> page, User user);

    boolean delete(String ids);


    boolean bindRole(Integer userId, List<Integer> list);
}
