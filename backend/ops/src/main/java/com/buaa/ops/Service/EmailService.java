package com.buaa.ops.Service;

import com.buaa.ops.Service.Emails.ReminderEmail;
import org.springframework.mail.MailException;

import java.io.File;

public interface EmailService {
    /**
     * Send a check email to the account Email
     * @param accountId The id of account in buffer field
     * @param email The email address of account
     * @throws MailException The Exceptions of mail sending
     */
    void sendCheckEmail(Integer accountId, String email, Boolean newAccount) throws MailException;

    /**
     * Send an article to reviewer's Email
     * @param email Target Email address
     * @param file File class of file you need to send
     * @param reminderEmail Object of ReminderEmail contains the information of email
     * @throws MailException The Exceptions of mail sending and encoding
     */
    void sendAttachmentEmail(String email, File file, ReminderEmail reminderEmail) throws MailException;

    /**
     * Send a reminder Email to Account's Email
     * @param email Target Email address
     * @param reminderEmail Object of ReminderEmail contains the information of email
     * @throws MailException The Exceptions of mail sending
     */
    void sendReminderEmail(String email, ReminderEmail reminderEmail) throws MailException;

}
