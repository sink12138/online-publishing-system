package com.buaa.ops.Service.Impl;

import com.buaa.ops.Entity.Account;
import com.buaa.ops.Entity.AccountBuffer;
import com.buaa.ops.Service.AccountService;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Account getAccountByAccountId(Integer accountId) {
        return null;
    }

    @Override
    public void addAccount(Account account) throws RepetitiveOperationException {

    }

    @Override
    public void addAccountBuffer(AccountBuffer accountBuffer) throws RepetitiveOperationException {

    }

    @Override
    public Account getAccountByEmail(String email) throws Exception {
        return null;
    }

    @Override
    public void deleteAccount(Integer accountId) throws ObjectNotFoundException {

    }

    @Override
    public Integer checkCode(String code) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void moveToAccount(Integer accountBufferId) throws RepetitiveOperationException {

    }

    @Override
    public void modifyInfos(Account newAccountInfos) throws ObjectNotFoundException {

    }

    @Override
    public Boolean cleanBuffer() {
        return null;
    }

    @Override
    public Account getAccountBySession(HttpSession session) throws LoginVerificationException {
        return null;
    }

    @Override
    public ArrayList<Account> getAccounts() {
        return null;
    }
}
