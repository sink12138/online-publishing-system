package com.buaa.ops.Service;

import com.buaa.ops.Entity.AccountBuffer;
import com.buaa.ops.Entity.Account;
import com.buaa.ops.Service.Exc.LoginVerificationException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AccountService {
    /**
     * Add a new account to Table Account,
     * Only after an account has been verified it can be remove from AccountBuffer to Account
     * @param account A new object of Class Account
     * @return Whether success
     */
    Boolean addAccount(Account account) throws Exception;

    /**
     * Add a new accountBuffer to Table AccountBuffer before register checking
     * @param accountBuffer A new object of Class AccountBuffer
     * @return Whether success
     */
    Boolean addAccountBuffer(AccountBuffer accountBuffer) throws Exception;

    /**
     * Select Account by email,
     * Check whether the email has been used when login and register
     * @param email Target email
     * @return An account with appropriate email
     */
    Account getAccount(String email) throws Exception;

    /**
     * Select AccountBuffer by email,
     * Check whether the email has been used when register
     * @param email Target email
     * @return An accountBuffer with appropriate email
     */
    AccountBuffer getAccountBuffer(String email) throws Exception;

    /**
     * Used by Admin to delete zombie account by accountId
     * @param accountId Target id
     * @return Whether success
     */
    Boolean deleteAccount(Integer accountId) throws Exception;

    /**
     * Check whether the code is valid
     * @param code The check code
     * @return The accountBufferId with this code
     */
    Integer checkCode(String code) throws Exception;

    /**
     * Move the account from AccountBuffer to Account,
     * Add Account and delete AccountBuffer
     * @param accountBufferId Target id in AccountBuffer
     * @return Whether success
     */
    Boolean moveToAccount(Integer accountBufferId) throws Exception;

    /**
     * Modify infos of current account,
     * If the attribute is null, just not modify
     * @param newAccountInfos An object of Account with modified infos
     * @return Whether success
     */
    Boolean modifyInfos(Account newAccountInfos) throws Exception;

    /**
     * Used by Admin to clean cache of AccountBuffer and Check
     * @return Whether success
     */
    Boolean cleanBuffer() throws Exception;

    /**
     * Used to verify identity and get information of account,
     * If appropriate account exists, return object of this account or throw LoginVerificationException
     * @param session Object of Httpsession
     * @return Account object
     * @throws LoginVerificationException If account not exists or password is not correct, throw LoginVerificationException
     */
    Account getAccountBySession(HttpSession session) throws LoginVerificationException;
}
