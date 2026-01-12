package com.xzit.utils;


import com.xzit.service.IFinanceService;
import com.xzit.service.IMailService;
import com.xzit.vo.MailVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class SendMailUtil {
    @Resource
    private IFinanceService financeService;
    @Resource
    private IMailService mailService;
    @Value("${spring.mail.username}")
    private String from;

    @Scheduled(cron = "0 0 20 * * ?")//每晚八点发送邮件
    public void sendMail() {
        StringBuffer sbf=new StringBuffer();
        if(financeService.sumRentPay()!=null){
            sbf.append("今日收入：")
                    .append("租金收入：")
                    .append(financeService.sumRentPay().getCountRentActual())
                    .append("，押金收入:")
                    .append(financeService.sumDeposit());
        }else{
            sbf.append("今日收入：")
                    .append("租金收入：")
                    .append(0)
                    .append("，押金收入:")
                    .append(0);
        }

        MailVo mailVo=new MailVo();
        mailVo.setFrom(from);
        mailVo.setTo("15213764412@163.com");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        mailVo.setSubject(date+"收入");
        mailVo.setContent(sbf.toString());
        mailService.sendMail(mailVo);
    }
}
