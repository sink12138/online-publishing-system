package com.buaa.ops.Dao;

import com.buaa.ops.Entity.AccountBuffer;

public interface AccountBufferDao {
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
