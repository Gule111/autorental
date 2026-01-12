package com.xzit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzit.mapper.FinanceMapper;
import com.xzit.service.IFinanceService;
import com.xzit.vo.FinanceCostVo;
import com.xzit.vo.FinanceNumDayVo;
import com.xzit.vo.FinanceNumMonthVo;
import jakarta.annotation.Resource;
import org.apache.poi.ss.formula.functions.Finance;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 31507
 */
@Service
public class FinanceServiceImpl extends ServiceImpl<FinanceMapper, Finance> implements IFinanceService {
    @Resource
    private FinanceMapper financeMapper;
    @Override
    public List<FinanceNumDayVo> countDayRental() {
        return financeMapper.countDayRental();
    }

    @Override
    public List<FinanceNumDayVo> countDayReturn() {
        return financeMapper.countDayReturn();
    }

    @Override
    public List<FinanceNumMonthVo> countMonthRental() {
        return financeMapper.countMonthRental();
    }

    @Override
    public List<FinanceNumMonthVo> countMonthReturn() {
        return financeMapper.countMonthReturn();
    }

    @Override
    public FinanceCostVo sumRentPay() {

        return financeMapper.sumRentPay();
    }

    @Override
    public Integer sumDeposit() {
        return financeMapper.sumDeposit();
    }

    @Override
    public FinanceCostVo sumRentPayMonth() {
        return financeMapper.sumRentPayMonth();
    }

    @Override
    public Integer sumDepositMonth() {
        return financeMapper.sumDepositMonth();
    }
}
