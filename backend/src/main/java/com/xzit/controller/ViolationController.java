package com.xzit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.AutoInfo;
import com.xzit.entity.Violation;
import com.xzit.service.IAutoInfoService;
import com.xzit.service.IViolationService;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Gule
 * @since 2025-06-01
 */
@RestController
@RequestMapping("/auto/violation")
public class ViolationController {
    @Resource
    private IViolationService violationService;
    @Resource
    private IAutoInfoService autoInfoService;

    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size,
                         @RequestBody Violation violation){
        Page<Violation> page=new Page<>(start,size);
        return Result.success(violationService.searchByPage(page,violation));
    }

    @PostMapping
    public Result save(@RequestBody Violation violation){
        AutoInfo autoInfo=autoInfoService.selectByAutoNum(violation.getAutoNum());
        violation.setAutoId(autoInfo.getId());
        return violationService.save(violation)?Result.success():Result.fail();
    }

    @PutMapping
    public Result update(@RequestBody Violation violation){
        AutoInfo autoInfo=autoInfoService.selectByAutoNum(violation.getAutoNum());
        violation.setAutoId(autoInfo.getId());
        return violationService.updateById(violation)?Result.success():Result.fail();
    }

    @DeleteMapping("{ids}")
    public Result delete(@PathVariable String ids){
        return violationService.delete(ids)?Result.success(): Result.fail();
    }


}
