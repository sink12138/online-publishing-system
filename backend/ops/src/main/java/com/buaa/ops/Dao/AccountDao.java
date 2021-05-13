package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Account;

public interface AccountDao {
    /**
     * Add a new account
     * @param account target object of Account
     */
    void addAccount(Account account);

    /**
     * Delete an account
     * @param accountId target id
     */
    void deleteAccount(Integer accountId);

    /**
     * Select an account by his/her Email
     * @param email target email
     * @return an object with this email
     */
    Account selectAccountByEmail(String email);


}
