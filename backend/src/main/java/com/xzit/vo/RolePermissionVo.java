package com.xzit.vo;

import com.xzit.entity.Permission;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 31507
 */
@Data
@Accessors(chain= true)
public class RolePermissionVo {
//    原有的权限id 数组
    private Object[] checkedList;

    private List<Permission> permissionList;
}
