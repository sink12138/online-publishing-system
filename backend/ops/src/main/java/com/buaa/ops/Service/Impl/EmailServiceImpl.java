package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.CheckDao;
import com.buaa.ops.Entity.Check;
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
import java.text.SimpleDateFormat;
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

    @Autowired
    CheckDao checkDao;

    @Value("${check.ip-address}")
    private String IP_ADDRESS;

    @Override
    public void sendCheckEmail(Integer accountId, Integer accountBufferId, String email) throws MailException, MessagingException{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String subject;
        String action;
        if (accountId == null) {
            subject = "注册OPS邮箱验证";
            action = "注册";
        } else {
            subject = "更改OPS邮箱验证";
            action = "更改";
        }
        String code = generateCode(accountId, accountBufferId);
        Check check = new Check(code, email, new Date());
        checkDao.insert(check);
        Context context = new Context();
        context.setVariable("action", action);
        context.setVariable("checkLink",  IP_ADDRESS +"/verify?code=" + code);
        context.setVariable("email", email);
        context.setVariable("createTime", formatDate());
        String process = templateEngine.process("CheckEmail.html", context);
        basicSetting(mimeMessage, subject, email, process);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendAttachmentEmail(String email, File file, ReminderEmail reminderEmail) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String subject = reminderEmail.getSubject();
        String title = reminderEmail.getTitle();
        String greetings = reminderEmail.getGreetings();
        String body = reminderEmail.getBody();
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("greetings", greetings);
        context.setVariable("body", "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + body);
        String process = templateEngine.process("ReminderEmail.html", context);
        MimeMessageHelper helper = basicSetting(mimeMessage, subject, email, process);
        helper.addAttachment(file.getName(), file);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendReminderEmail(String email, ReminderEmail reminderEmail) throws MailException , MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String subject = reminderEmail.getSubject();
        String title = reminderEmail.getTitle();
        String greetings = reminderEmail.getGreetings();
        String body = reminderEmail.getBody();
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("greetings", greetings);
        context.setVariable("body", "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + body);
        String process = templateEngine.process("ReminderEmail.html", context);
        basicSetting(mimeMessage, subject, email, process);
        javaMailSender.send(mimeMessage);
    }

    private String generateCode(Integer accountId, Integer accountBufferId) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < CODE_BITS; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return accountId == null ? stringBuffer.toString() +  accountBufferId : stringBuffer.toString() + accountBufferId + '_' +accountId;
    }

    MimeMessageHelper basicSetting(MimeMessage mimeMessage, String subject, String toAddress, String process) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("OPS | " + subject);
        helper.setFrom(FROM_ADDRESS);
        helper.setTo(toAddress);
        helper.setSentDate(new Date());
        helper.setText(process, true);
        return helper;
    }

    private String formatDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
