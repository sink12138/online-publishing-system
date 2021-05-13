package com.buaa.ops.Service.Impl;

import com.buaa.ops.Entity.Check;
import com.buaa.ops.Service.CheckService;
import com.buaa.ops.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailServiceImpl implements EmailService {
    @Autowired
    CheckService checkService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Override
    public void SendCheckMail(Integer accountBufferId, String email) throws Exception {
        Check check = checkService.GenerateNewCode(accountBufferId, email);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("OPS | 注册OPS邮箱验证");
        helper.setFrom("ruangong202105@163.com");
        helper.setTo(email);
        helper.setSentDate(new Date());

        Context context = new Context();
        context.setVariable("mail", email);
        context.setVariable("url", "http://localhost:8090/check?code=" + check.getCode());
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        context.setVariable("createTime", simpleDateFormat.format(date));
        String process = templateEngine.process("mail.html", context);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
        helper.setText(process, true);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void SendArticle(String email, File file) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setSubject("OPS | 文章发送");
        helper.setFrom("ruangong202105@163.com");
        helper.setTo(email);
        helper.setSentDate(new Date());

        //设置邮件内容
        helper.setText("这是您要查阅的文章");
        helper.addAttachment(MimeUtility.encodeWord(file.getName(),"utf-8","B"), file);
        javaMailSender.send(mimeMessage);
    }
}
