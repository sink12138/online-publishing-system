package com.buaa.ops.Service;

public interface AccountService {
    /**
     * Register a new account: insert the information into AccountBuffer and send a check email to his/her email
     * @param email account email
     * @param password account password
     */
    void register(String email, String password);



}
