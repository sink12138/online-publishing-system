package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.AccountBufferDao;
import com.buaa.ops.Dao.AccountDao;
import com.buaa.ops.Dao.CheckDao;
import com.buaa.ops.Entity.Account;
import com.buaa.ops.Entity.AccountBuffer;
import com.buaa.ops.Entity.Check;
import com.buaa.ops.Service.AccountService;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Override
    public Account getAccountByAccountId(Integer accountId) {
        return accountDao.selectById(accountId);
    }

    @Autowired
    AccountBufferDao accountBufferDao;

    @Override
    public void addAccount(Account account) throws RepetitiveOperationException {
        Account another = accountDao.selectByEmail(account.getEmail());
        if (another != null) {
            throw new RepetitiveOperationException();
        }
        accountDao.insert(account);
    }

    @Override
    public void addAccountBuffer(AccountBuffer accountBuffer) throws RepetitiveOperationException {
        AccountBuffer another = accountBufferDao.selectByEmail(accountBuffer.getEmail());
        if (another != null) {
            throw new RepetitiveOperationException();
        }
        accountBufferDao.insert(accountBuffer);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountDao.selectByEmail(email);
    }

    @Override
    public void deleteAccount(Integer accountId) throws ObjectNotFoundException {
        if (accountDao.deleteById(accountId) == 0) {
            throw new ObjectNotFoundException();
        }
    }

    @Autowired
    CheckDao checkDao;

    @Value("${check.validTime}")
    private Integer VALID_TIME;

    @Value("${check.codeBits}")
    private Integer CODE_BITS;

    @Override
    public Integer checkCode(String code) throws ObjectNotFoundException {
        Check check = checkDao.selectByCode(code);
        Date beforeDate = new Date(new Date().getTime() - VALID_TIME);
        if (check == null || check.getCheckingTime().getTime() < beforeDate.getTime()) {
            throw new ObjectNotFoundException();
        }
        Integer len = check.getCode().length();
        checkDao.deleteById(check.getCheckId());
        return Integer.parseInt(check.getCode().substring(0, len - CODE_BITS));
    }

    @Override
    public void moveToAccount(Integer accountBufferId) {
        AccountBuffer accountBuffer = accountBufferDao.selectById(accountBufferId);
        Account account = new Account(accountBuffer.getEmail(), accountBuffer.getPassword(), null);
        accountDao.insert(account);
        accountBufferDao.deleteById(accountBufferId);
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
