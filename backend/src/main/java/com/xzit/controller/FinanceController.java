package com.xzit.controller;

import com.xzit.mapper.FinanceMapper;
import com.xzit.service.IFinanceService;
import com.xzit.utils.IntegerUtil;
import com.xzit.utils.Result;
import com.xzit.vo.FinanceCostVo;
import com.xzit.vo.FinanceNumDayVo;
import com.xzit.vo.FinanceNumMonthVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 * 财务管理控制器
 * 提供日租、月租、归还等财务数据统计相关的API接口
 *
 * @author 31507
 */
@RestController
@RequestMapping("/auto/finance")
public class FinanceController {
    @Resource
    private IFinanceService financeService;

    /**
     * 统计日租数据
     * 返回每小时的租赁数量统计
     *
     * @return Result 包含小时列表和对应租赁数量列表的结果对象
     */
    @GetMapping("/countDayRental")
    public Result countDayRental(){
        //统计日租
        List<FinanceNumDayVo> rentalList = financeService.countDayRental();
        List<List<Object>> list=new ArrayList<>();
        List<Object> hoursList=new ArrayList<>();
        List<Object> rentalNumList=new ArrayList<>();
        rentalList.forEach(item->{
            hoursList.add(item.getHours());
            rentalNumList.add(item.getRentalNum());
        });
        list.add(hoursList);
        list.add(rentalNumList);
        return Result.success(list);
    }

    /**
     * 统计日归还数据
     * 返回每小时的归还数量统计
     *
     * @return Result 包含小时列表和对应归还数量列表的结果对象
     */
    @RequestMapping("countDayReturn")
    public Result countDayReturn(){
        //统计日租
        List<FinanceNumDayVo> returnList = financeService.countDayReturn();
        List<List<Object>> list=new ArrayList<>();
        List<Object> hoursList=new ArrayList<>();
        List<Object> returnNumList=new ArrayList<>();
        returnList.forEach(item->{
            hoursList.add(item.getHours());
            returnNumList.add(item.getReturnNum());
        });
        list.add(hoursList);
        list.add(returnNumList);
        return Result.success(list);
    }


    /**
     * 统计日成本数据
     * 返回租赁支付总额和押金总额
     *
     * @return Result 包含成本信息的FinanceCostVo对象
     */
    @GetMapping("countDayCost")
    public Result countDayCost(){
        FinanceCostVo vo=financeService.sumRentPay();
        Integer deposit=financeService.sumDeposit();
        vo.setCountDeposit(deposit);
        return Result.success(vo);
    }

    /**
     * 统计月租数据
     * 返回每天的租赁数量统计
     *
     * @return Result 包含天数列表和对应租赁数量列表的结果对象
     */
    @RequestMapping("countMonthRental")
    public Result countMonthRental(){
        //统计日租
        List<FinanceNumMonthVo> rentalList = financeService.countMonthRental();
        List<List<Object>> list=new ArrayList<>();
        List<Object> daysList=new ArrayList<>();
        List<Object> rentalNumList=new ArrayList<>();
        rentalList.forEach(item->{
            daysList.add(item.getDays());
            rentalNumList.add(item.getRentalNum());
        });
        list.add(daysList);
        list.add(rentalNumList);
        return Result.success(list);
    }

    /**
     * 统计月归还数据
     * 返回每天的归还数量统计
     *
     * @return Result 包含天数列表和对应归还数量列表的结果对象
     */
    @RequestMapping("countMonthReturn")
    public Result countMonthReturn(){
        //统计日租
        List<FinanceNumMonthVo> returnList = financeService.countMonthReturn();
        List<List<Object>> list=new ArrayList<>();
        List<Object> daysList=new ArrayList<>();
        List<Object> returnNumList=new ArrayList<>();
        returnList.forEach(item->{
            daysList.add(item.getDays());
            returnNumList.add(item.getReturnNum());
        });
        list.add(daysList);
        list.add(returnNumList);
        return Result.success(list);
    }

    /**
     * 统计月成本数据
     * 返回月度租赁支付总额和押金总额
     *
     * @return Result 包含月度成本信息的FinanceCostVo对象
     */
    @GetMapping("countMonthCost")
    public Result countMonthCost(){
        FinanceCostVo vo=financeService.sumRentPayMonth();
        Integer deposit=financeService.sumDepositMonth();
        vo.setCountDeposit(deposit);
        return Result.success(vo);
    }

}
