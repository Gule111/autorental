package com.xzit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.AutoInfo;
import com.xzit.entity.Maintain;
import com.xzit.service.IAutoInfoService;
import com.xzit.service.IMaintainService;
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
/**
 * 车辆保养管理控制器
 * 提供车辆保养相关的增删改查接口
 */
@RestController
@RequestMapping("/auto/maintain")
public class MaintainController {
    @Resource
    private IMaintainService maintainService;
    @Resource
    private IAutoInfoService autoInfoService;

    /**
     * 分页查询保养记录
     * @param start 起始页码
     * @param size 每页大小
     * @param maintain 保养记录查询条件
     * @return 分页查询结果
     */
    @PostMapping("{start}/{size}")
    public Result search(@PathVariable int start,
                         @PathVariable int size,
                         @RequestBody Maintain maintain){
        Page<Maintain> page = new Page<>(start, size);
        return Result.success(maintainService.searchByPage(page, maintain));
    }

    /**
     * 保存保养记录
     * @param maintain 保养记录信息
     * @return 保存结果
     */
    @PostMapping
    public Result save(@RequestBody Maintain maintain) {
        //设置保养关联的车辆信息
        AutoInfo autoInfo = autoInfoService.selectByAutoNum(maintain.getAutoNum());
        maintain.setAutoId(autoInfo.getId());
        //将车辆实保次数+1
        autoInfo.setActualNum(autoInfo.getActualNum() + 1);
        autoInfoService.updateById(autoInfo);
        return maintainService.save(maintain)?Result.success():Result.fail();
    }

    /**
     * 更新保养记录
     * @param maintain 保养记录信息
     * @return 更新结果
     */
    @PutMapping
    public Result update(@RequestBody Maintain maintain) {

        return maintainService.updateById(maintain)?Result.success():Result.fail();
    }

    /**
     * 删除保养记录
     * @param ids 保养记录ID（多个ID用逗号分隔）
     * @return 删除结果
     */
    @DeleteMapping("{ids}")
    public Result delete(@PathVariable String ids) {
        return maintainService.delete(ids)?Result.success():Result.fail();
    }

}

