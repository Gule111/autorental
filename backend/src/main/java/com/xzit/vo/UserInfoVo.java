package com.xzit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 31507
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo implements Serializable {
    /**
     * 用户类
     * <p>
     * 该类用于表示系统中的用户信息，包括用户的唯一标识符、姓名、头像、简介和角色
     */
    private Integer id; // 用户唯一标识符
    private String name; // 用户姓名
    private String avatar; // 用户头像的URL或路径
    private String introduction; // 用户的简短自我介绍
    private Object[] roles; // 用户的角色或权限，以数组形式存储

}
