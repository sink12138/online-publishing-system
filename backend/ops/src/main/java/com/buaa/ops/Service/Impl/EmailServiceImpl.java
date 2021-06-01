package com.buaa.ops.Service.Impl;

import com.buaa.ops.Service.EmailService;
import com.buaa.ops.Service.Emails.ReminderEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${check.code-bits}")
    private Integer CODE_BITS;

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Override
    public void sendCheckEmail(Integer accountId, String email, Boolean newAccount) throws MailException, MessagingException{
        String subject;
        if (newAccount) {
            subject = "注册OPS邮箱验证";
        } else {
            subject = "更改OPS邮箱验证";
        }
        MimeMessageHelper helper = basicSetting(subject, email);
        Context context = new Context();
        //context.setVariable();
    }

    @Override
    public void sendAttachmentEmail(String email, File file, ReminderEmail reminderEmail) throws MailException, MessagingException {

    }

    @Override
    public void sendReminderEmail(String email, ReminderEmail reminderEmail) throws MailException , MessagingException {

    }

    private String generateCode(Integer accountBufferId) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < CODE_BITS; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString() + accountBufferId;
    }

    MimeMessageHelper basicSetting(String subject, String toAddress) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("OPS | " + subject);
        helper.setFrom(FROM_ADDRESS);
        helper.setTo(toAddress);
        helper.setSentDate(new Date());
        return helper;
    }
}
