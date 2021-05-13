package com.buaa.ops.Service;

import java.io.File;

public interface EmailService {
    /**
     * Send a check email to the account Email
     * @param accountBufferId the id of account in buffer field
     * @param email the email address of account
     * @throws Exception the Exceptions of mail sending
     */
    void SendCheckMail(Integer accountBufferId, String email) throws Exception;

    /**
     * Send an article to the email
     * @param email target email address
     * @param file File class of file you need to send
     * @throws Exception the Exceptions of mail sending and encoding
     */
    void SendArticle(String email, File file) throws Exception;
}
