package com.buaa.ops.Service;

import com.buaa.ops.Entity.Check;

public interface CheckService {
    /**
     * Add a new Check into database
     * @param check an object of Check
     */
    void AddCheck(Check check);

    /**
     * Select all Checks by code
     * @param code the String attribute code of Check
     * @return Check an object of Check
     */
    Check SelectCheckByCode(String code);

    /**
     * Select all Checks by email
     * @param email the String email
     * @return Check an object with the email
     */
    Check SelectCheckByEmail(String email);

    /**
     * Delete an object of Check by checkId
     * @param checkId the int attribute of Check
     */
    void DeleteCheck(Integer checkId);

    /**
     * Generate a new Check object by id and email
     * @param accountBufferId the id of account in buffer field
     * @param email the email of account
     * @return a new Check
     */
    Check GenerateNewCode(Integer accountBufferId, String email);
}
