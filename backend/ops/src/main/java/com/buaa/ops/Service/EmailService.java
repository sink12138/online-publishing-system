package com.buaa.ops.Service;

import java.io.File;

public interface EmailService {
    /**
     * Send a check email to the account Email
     * @param accountId The id of account in buffer field
     * @param email The email address of account
     * @return Whether success
     * @throws Exception The Exceptions of mail sending
     */
    Boolean sendCheckEmail(Integer accountId, String email, Boolean newAccount) throws Exception;

    /**
     * Send an article to reviewer's Email
     * @param email Target Email address
     * @param file File class of file you need to send
     * @return Whether success
     * @throws Exception The Exceptions of mail sending and encoding
     */
    Boolean sendAttachmentEmail(String email, File file) throws Exception;

    /**
     * Send a reminder Email to Account's Email
     * @param email Target Email
     * @param type The type of remind
     * @return Whether success
     * @throws Exception The Exceptions of mail sending
     */
    Boolean sendReminderEmail(String email, String type) throws Exception;
}
