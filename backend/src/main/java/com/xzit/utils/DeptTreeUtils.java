package com.xzit.utils;

import com.xzit.entity.Dept;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 31507
 */
public class DeptTreeUtils {

    public static List<Dept> buildTree(List<Dept> list,int pid){
        List<Dept> deptTree=new ArrayList<>();
        Optional.ofNullable(list).orElse(new ArrayList<>()).stream()
                .filter(dept -> dept.getPid().equals(pid))
                .forEach(dept -> {
                    Dept dept1=new Dept();
                    BeanUtils.copyProperties(dept,dept1);
//                    关键是这里传入的是id而不是pid，所以才会一直递归
                    dept1.setChildren(buildTree(list,dept.getId()));
                    deptTree.add(dept1);
                });
        return deptTree;
    }
}
