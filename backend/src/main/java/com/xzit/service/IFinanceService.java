package com.xzit.service;

import com.xzit.vo.FinanceCostVo;
import com.xzit.vo.FinanceNumDayVo;
import com.xzit.vo.FinanceNumMonthVo;

import java.util.List;

/**
 * @author 31507
 */
public interface IFinanceService {
//    统计出租数量
    List<FinanceNumDayVo> countDayRental();
//    统计归还数量
    List<FinanceNumDayVo> countDayReturn();
    List<FinanceNumMonthVo> countMonthRental();
    List<FinanceNumMonthVo> countMonthReturn();

    FinanceCostVo sumRentPay();

    Integer sumDeposit();
    FinanceCostVo sumRentPayMonth();

    Integer sumDepositMonth();


}
