package com.xzit.service.impl;

import com.xzit.service.IMailService;
import com.xzit.vo.MailVo;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author 31507
 */
@Service
public class MailServiceImpl  implements IMailService {
    @Resource
    private JavaMailSender  javaMailSender;

    @Override
    public void sendMail(MailVo mail) {
        // 创建一个SimpleMailMessage对象用于设置邮件信息
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件的发送者
        message.setFrom(mail.getFrom());
        // 设置邮件的接收者
        message.setTo(mail.getTo());
        // 设置邮件的主题
        message.setSubject(mail.getSubject());
        // 设置邮件的内容
        message.setText(mail.getContent());
        // 设置邮件的抄送者，这里将发送者作为抄送人
        message.setCc(mail.getFrom());
        // 使用javaMailSender发送邮件
        javaMailSender.send(message);
    }
}
