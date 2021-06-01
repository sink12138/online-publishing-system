package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Account;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface AccountDao {

    ArrayList<Account> selectAll();

    Account selectById(Integer id);

    Account selectByEmail(String email);

    Integer insert(Account account);

    Integer deleteById(Integer id);

    /**
     * Password can't be null,
     * attribute realName will be update to null if param realName is null
     */
    Integer updateById(Account newAccountInfos);
}
