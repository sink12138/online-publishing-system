package com.buaa.ops.Service.Impl;

import com.buaa.ops.Service.EmailService;
import com.buaa.ops.Service.Emails.ReminderEmail;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public void sendCheckEmail(Integer accountId, String email, Boolean newAccount) throws MailException {

    }

    @Override
    public void sendAttachmentEmail(String email, File file, ReminderEmail reminderEmail) throws MailException {

    }

    @Override
    public void sendReminderEmail(String email, ReminderEmail reminderEmail) throws MailException {

    }
}
