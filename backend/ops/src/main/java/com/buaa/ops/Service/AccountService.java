package com.buaa.ops.Service;

import com.buaa.ops.Entity.AccountBuffer;
import com.buaa.ops.Entity.Account;

import java.util.Map;

public interface AccountService {
    /**
     * Add a new account to Table Account
     * @param account A new object of Class Account
     * @return Whether success
     */
    Boolean addAccount(Account account);

    /**
     * Add a new accountBuffer to Table AccountBuffer before register checking
     * @param accountBuffer A new object of Class AccountBuffer
     * @return Whether success
     */
    Boolean addAccountBuffer(AccountBuffer accountBuffer);

    /**
     * Select Account by email
     * Check whether the email has been used when login and register
     * @param email Target email
     * @return An account with appropriate email
     */
    Account getAccount(String email);

    /**
     * Select AccountBuffer by email
     * Check whether the email has been used when register
     * @param email Target email
     * @return An accountBuffer with appropriate email
     */
    AccountBuffer getAccountBuffer(String email);

    /**
     * Used by Admin to delete zombie account by accountId
     * @param accountId Target id
     * @return Whether success
     */
    Boolean deleteAccount(Integer accountId);

    /**
     * Check whether the code is valid
     * @param code The check code
     * @return The accountBufferId with this code
     */
    Integer checkCode(String code);

    /**
     * Move the account from AccountBuffer to Account
     * Add Account and delete AccountBuffer
     * @param accountBufferId Target id in AccountBuffer
     * @return Whether success
     */
    Boolean moveToAccount(Integer accountBufferId);

    /**
     * Modify infos of current account
     * If the attribute is null, just not modify
     * @param newAccountInfos An object of Account with modified infos
     * @return Whether success
     */
    Boolean modifyInfos(Account newAccountInfos);

    /**
     * Get the role of this account and the appropriate role id
     * @param accountId The id of account
     * @return A Map whose key is a String standing for account's role and whose value is role id
     */
    Map<String, Integer> getRoleId(Integer accountId);

    /**
     * Used by Admin to clean cache of AccountBuffer and Check
     * @return Whether success
     */
    Boolean cleanBuffer();
}
