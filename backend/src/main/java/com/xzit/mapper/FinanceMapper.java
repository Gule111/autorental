package com.xzit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzit.vo.FinanceCostVo;
import com.xzit.vo.FinanceNumDayVo;
import com.xzit.vo.FinanceNumMonthVo;
import org.apache.poi.ss.formula.functions.Finance;

import java.util.List;

/**
 * @author 31507
 */
public interface FinanceMapper extends BaseMapper<Finance> {
    List<FinanceNumDayVo> countDayRental();

    List<FinanceNumDayVo> countDayReturn();

    List<FinanceNumMonthVo> countMonthRental();

    List<FinanceNumMonthVo> countMonthReturn();

    FinanceCostVo sumRentPay();

    Integer sumDeposit();
    FinanceCostVo sumRentPayMonth();
    Integer sumDepositMonth();

}
