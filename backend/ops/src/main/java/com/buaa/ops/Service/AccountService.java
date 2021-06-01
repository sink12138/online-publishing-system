package com.buaa.ops.Service;

import com.buaa.ops.Entity.AccountBuffer;
import com.buaa.ops.Entity.Account;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

public interface AccountService {
    /**
     * Get instance of Account by target id
     * @param accountId Target id of account
     * @return Instance of Account with target id
     */
    Account getAccountByAccountId(Integer accountId);

    /**
     * Get instance of AccountBuffer by target id
     * @param accountBufferId Target id of account
     * @return Instance of Account with target id
     */
    AccountBuffer getAccountBufferById(Integer accountBufferId);

    /**
     * Add a new account to Table Account,
     * Used by Admin to add new Account
     * @param account A new object of Class Account
     */
    void addAccount(Account account) throws RepetitiveOperationException;

    /**
     * Add a new accountBuffer to Table AccountBuffer before register checking
     * @param accountBuffer A new object of Class AccountBuffer
     */
    void addAccountBuffer(AccountBuffer accountBuffer) throws RepetitiveOperationException;

    /**
     * Select Account by email,
     * Check whether the email has been used when login and register
     * @param email Target email
     * @return An account with appropriate email
     */
    Account getAccountByEmail(String email);

    /**
     * Used by Admin to delete zombie account by accountId
     * @param accountId Target id
     */
    void deleteAccount(Integer accountId) throws ObjectNotFoundException;

    /**
     * Check whether the code is valid
     * @param code The check code
     * @return The accountBufferId and accountId
     */
    Map<String, Integer> checkCode(String code) throws ObjectNotFoundException;

    /**
     * Move the account from AccountBuffer to Account,
     * Add Account and delete AccountBuffer
     * @param accountBufferId Target id in AccountBuffer
     */
    void moveToAccount(Integer accountBufferId);

    /**
     * Modify infos of current account,
     * If the attribute is null, just not modify
     * @param newAccountInfos An object of Account with modified infos
     */
    void modifyInfos(Account newAccountInfos) throws ObjectNotFoundException;

    /**
     * Used by Admin to clean cache of AccountBuffer and Check
     * @return Whether success
     */
    Boolean cleanBuffer();

    /**
     * Used to verify identity and get information of account,
     * If appropriate account exists, return object of this account or throw LoginVerificationException
     * @param session Object of Httpsession
     * @return Account object
     * @throws LoginVerificationException If account not exists or password is not correct, throw LoginVerificationException
     */
    Account getAccountBySession(HttpSession session) throws LoginVerificationException;

    /**
     * Used by admin to check all the accounts
     * @return An instance of ArrayList containing all the accounts currently
     */
    ArrayList<Account> getAccounts();
}
