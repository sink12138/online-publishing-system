package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.*;
import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.AccountService;
import com.buaa.ops.Service.Exc.IllegalAuthorityException;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;

    @Override
    public Account getAccountByAccountId(Integer accountId) {
        return accountDao.selectById(accountId);
    }

    @Override
    public AccountBuffer getAccountBufferById(Integer accountBufferId) {
        return accountBufferDao.selectById(accountBufferId);
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
        Account account = accountDao.selectByEmail(accountBuffer.getEmail());
        if (account != null) {
            throw new RepetitiveOperationException();
        }
        AccountBuffer another = accountBufferDao.selectByEmail(accountBuffer.getEmail());
        if (another != null) {
            accountBufferDao.deleteById(another.getAccountBufferId());
        }
        accountBufferDao.insert(accountBuffer);
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountDao.selectByEmail(email);
    }

    @Autowired
    AuthorDao authorDao;

    @Autowired
    EditorDao editorDao;

    @Autowired
    ReviewerDao reviewerDao;

    @Override
    public void deleteAccount(Integer accountId) throws ObjectNotFoundException, IllegalAuthorityException {
        Author author = authorDao.selectByAccountId(accountId);
        Editor editor = editorDao.selectByAccountId(accountId);
        Reviewer reviewer = reviewerDao.selectByAccountId(accountId);
        if (author != null || editor != null || reviewer != null) {
            throw new IllegalAuthorityException();
        }
        if (accountDao.deleteById(accountId) == 0) {
            throw new ObjectNotFoundException();
        }
    }

    @Autowired
    CheckDao checkDao;

    @Value("${check.valid-time}")
    private Integer VALID_TIME;

    @Value("${check.code-bits}")
    private Integer CODE_BITS;

    @Override
    public Map<String, Integer> checkCode(String code) throws ObjectNotFoundException {
        Check check = checkDao.selectByCode(code);
        Date deadline = new Date(new Date().getTime() - VALID_TIME);
        if (check == null || check.getCheckingTime().getTime() < deadline.getTime()) {
            throw new ObjectNotFoundException();
        }
        Integer len = check.getCode().length();
        checkDao.deleteById(check.getCheckId());
        Map<String, Integer> id = new HashMap<>();
        if (code.contains("_")) {
            int pos = code.indexOf('_');
            id.put("accountBufferId", Integer.parseInt(code.substring(CODE_BITS, pos)));
            id.put("accountId", Integer.parseInt(code.substring(pos + 1)));
        } else {
            id.put("accountBufferId", Integer.parseInt(code.substring(CODE_BITS)));
        }
        return id;
    }

    @Override
    public void deleteAccountBufferById(Integer id) {
        accountBufferDao.deleteById(id);
    }

    @Override
    public void modifyInfos(Account newAccountInfos) throws ObjectNotFoundException {
        if (accountDao.updateById(newAccountInfos) == 0) {
            throw new ObjectNotFoundException();
        }
    }

    @Override
    public Boolean cleanBuffer() {
        Date deadline = new Date(new Date().getTime() - VALID_TIME);
        java.sql.Timestamp deadlineSQL = new java.sql.Timestamp(deadline.getTime());
        ArrayList<Check> invalidChecks = checkDao.selectInvalid(deadlineSQL);
        for (Check check : invalidChecks) {
            String code = check.getCode();
            int accountBufferId;
            if (code.contains("_")) {
                int pos = code.indexOf('_');
                accountBufferId = Integer.parseInt(code.substring(CODE_BITS, pos));
            } else {
                accountBufferId = Integer.parseInt(check.getCode().substring(CODE_BITS));
            }
            if ((accountBufferDao.selectById(accountBufferId) != null && accountBufferDao.deleteById(accountBufferId) == 0) || checkDao.deleteById(check.getCheckId()) == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Account getAccountBySession(HttpSession session) throws LoginVerificationException {
        String email = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
        Account account = accountDao.selectByEmail(email);
        if (account == null || !account.getPassword().equals(password)) {
            throw new LoginVerificationException();
        }
        return account;
    }

    @Override
    public ArrayList<Account> getAccounts() {
        return accountDao.selectAll();
    }
}
