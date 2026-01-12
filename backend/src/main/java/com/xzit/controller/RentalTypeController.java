package com.xzit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzit.entity.RentalType;
import com.xzit.service.IRentalTypeService;
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
@RequestMapping("/auto/rentalType")
public class RentalTypeController {
    @Resource
    private IRentalTypeService rentalTypeService;

    @PostMapping("{start}/{size}")
    public Result search(@PathVariable int start, @PathVariable int size,
                         @RequestBody RentalType rentalType){
        Page<RentalType> page=new Page<>(start,size);
        return Result.success(rentalTypeService.selectPage(page,rentalType));
    }
    @PostMapping
    public Result save(@RequestBody RentalType rentalType){
        return rentalTypeService.save(rentalType)?Result.success():Result.fail();
    }

    @PutMapping
    public Result update(@RequestBody RentalType rentalType){
        return rentalTypeService.updateById(rentalType)?Result.success():Result.fail();
    }
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids){
        return rentalTypeService.delete(ids)?Result.success():Result.fail();
    }
    @GetMapping
    public Result selectAll(){
        return Result.success(rentalTypeService.list());
    }
}
