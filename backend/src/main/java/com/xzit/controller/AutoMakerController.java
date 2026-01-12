package com.xzit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.AutoMaker;
import com.xzit.service.IAutoMakerService;
import com.xzit.utils.PinYinUtils;
import com.xzit.utils.Result;
import jakarta.annotation.Resource;
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
@RequestMapping("/auto/autoMaker")
public class AutoMakerController {
    @Resource
    private IAutoMakerService autoMakerService;

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/{start}/{size}")
    public Result search(@PathVariable int start,
                         @PathVariable int size,
                         @RequestBody AutoMaker autoMaker) {
        Page<AutoMaker> search = autoMakerService.search(start, size, autoMaker);
        return Result.success(search).setData(search);
    }
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String  ids) {
        String[] split = ids.split(",");
        List<Integer> list = Arrays.stream(split).map(Integer::parseInt).toList();
        return autoMakerService.removeByIds(list) ? Result.success() : Result.fail();
    }
    @PostMapping
    public Result save(@RequestBody AutoMaker autoMaker) {
        autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
        return autoMakerService.save(autoMaker) ? Result.success() : Result.fail();
    }
    @PutMapping
    public Result update(@RequestBody AutoMaker autoMaker) {
        autoMaker.setOrderLetter(PinYinUtils.getPinYin(autoMaker.getName()));
        return autoMakerService.updateById(autoMaker) ? Result.success() : Result.fail();
    }
    @GetMapping
    public Result selectAll() {
        return Result.success().setData(autoMakerService.list());
    }

}
