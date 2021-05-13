package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Check;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckDao {
    /**
     * Select all Checks by code
     * @param code the String attribute code of Check
     * @return Check an object of Check
     */
    Check SelectCheckByCode(String code);

    /**
     * Add a new Check into database
     * @param check an object of Check
     */
    void AddCheck(Check check);

    /**
     * Delete an object of Check by checkId
     * @param checkId the int attribute of Check
     */
    void DeleteCheck(Integer checkId);

    /**
     * Select all Checks by email
     * @param email the String email
     * @return Check an object with the email
     */
    Check SelectCheckByEmail(String email);

    /**
     * Update the original check with the email to the new one
     * @param newCheck the new check
     */
    void UpdateCheck(Check newCheck);
}
