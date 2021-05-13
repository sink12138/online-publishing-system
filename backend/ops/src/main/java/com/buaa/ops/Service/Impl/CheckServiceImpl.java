package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.CheckDao;
import com.buaa.ops.Entity.Check;
import com.buaa.ops.Service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class CheckServiceImpl implements CheckService {
    private static final Integer codeBites = 8;

    @Autowired
    CheckDao checkDao;

    @Override
    public void AddCheck(Check check) {
        checkDao.AddCheck(check);
    }

    @Override
    public Check SelectCheckByCode(String code) {
        return checkDao.SelectCheckByCode(code);
    }

    @Override
    public Check SelectCheckByEmail(String email) {
        return checkDao.SelectCheckByEmail(email);
    }

    @Override
    public void DeleteCheck(Integer checkId) {
        checkDao.DeleteCheck(checkId);
    }

    @Override
    public Check GenerateNewCode(Integer accountBufferId, String email) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < codeBites; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        Check check = new Check(stringBuffer.toString() + accountBufferId, email, new Date());
        Check checkBefore = checkDao.SelectCheckByEmail(email);
        if (checkBefore == null) {
            checkDao.AddCheck(check);
        } else {
            checkDao.UpdateCheck(check);
        }
        return check;
    }
}
