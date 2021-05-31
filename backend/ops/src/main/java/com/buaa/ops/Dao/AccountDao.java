package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Account;
import com.buaa.ops.Entity.AccountBuffer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
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
    
    /**
     * Delete accountBuffer by id
     * @param accountBufferId target id
     */
    void deleteAccountBuffer(Integer accountBufferId);

    /**
     * Add a new AccountBuffer into database
     * @param accountBuffer a new object of AccountBuffer
     */
    void addAccountBuffer(AccountBuffer accountBuffer);

    /**
     * Select AccountBuffer by id
     * @param accountBufferId target id
     * @return a object of AccountBuffer with the id
     */
    AccountBuffer selectAccountBuffer(Integer accountBufferId);

}
