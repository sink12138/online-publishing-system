package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Check;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CheckDao {
    /**
     * @return the code within the valid period
     */
    Check selectByCode(String code);

    /**
     * @return All the invalid instances of Check
     */
    ArrayList<Check> selectInvalid();

    Integer deleteById(Integer id);
}
