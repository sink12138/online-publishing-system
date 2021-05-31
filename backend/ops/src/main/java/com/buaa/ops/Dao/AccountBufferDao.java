package com.buaa.ops.Dao;

import com.buaa.ops.Entity.AccountBuffer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountBufferDao {

    AccountBuffer selectById(Integer id);

    AccountBuffer selectByEmail(String email);

    Integer insert(AccountBuffer accountBuffer);

    Integer deleteById(Integer id);
}
