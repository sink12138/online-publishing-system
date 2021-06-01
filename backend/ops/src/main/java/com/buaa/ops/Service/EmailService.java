package com.buaa.ops.Service;

import com.buaa.ops.Service.Emails.ReminderEmail;
import org.springframework.mail.MailException;

import javax.mail.MessagingException;
import java.io.File;

public interface EmailService {
    /**
     * Send a check email to the account Email
     * @param accountId The id of account, if the param is null = new account register
     * @param accountBufferId id of accountBuffer
     * @param email The email address of account
     * @throws MailException The Exceptions of mail sending
     */
    void sendCheckEmail(Integer accountId, Integer accountBufferId, String email) throws MailException, MessagingException;

    /**
     * Send an article to reviewer's Email
     * @param email Target Email address
     * @param file File class of file you need to send
     * @param reminderEmail Object of ReminderEmail contains the information of email
     * @throws MailException The Exceptions of mail sending and encoding
     */
    void sendAttachmentEmail(String email, File file, ReminderEmail reminderEmail) throws MailException, MessagingException;

    /**
     * Send a reminder Email to Account's Email
     * @param email Target Email address
     * @param reminderEmail Object of ReminderEmail contains the information of email
     * @throws MailException The Exceptions of mail sending
     */
    void sendReminderEmail(String email, ReminderEmail reminderEmail) throws MailException, MessagingException;

}
