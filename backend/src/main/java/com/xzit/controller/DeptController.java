package com.xzit.controller;

import com.xzit.entity.Dept;
import com.xzit.service.IDeptService;
import com.xzit.utils.DeptTreeUtils;
import com.xzit.utils.Result;
import io.swagger.models.auth.In;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/auto/dept")
public class DeptController {

    @Resource
    private IDeptService deptService;
    @PostMapping
    public Result list(@RequestBody Dept dept){
        System.out.println("dept = " + dept);
        return Result.success().setData(deptService.selectList(dept));
    }
    @GetMapping
    public Result tree(){
        return Result.success().setData(deptService.selectTree());
    }
    @PostMapping("save")
    public Result save(@RequestBody Dept dept){
        return deptService.saveOrUpdate(dept)?Result.success():Result.fail();
    }
    @PutMapping
    public Result update(@RequestBody Dept dept){
        return deptService.updateById(dept)?Result.success():Result.fail();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return deptService.removeById(id)?Result.success():Result.fail();
    }
    @GetMapping("/{id}")
    public Result hasChildren(@PathVariable Integer id){
        return deptService.hasChildren(id)?Result.success().setMessage("有"):Result.success().setMessage("无");
    }



}
