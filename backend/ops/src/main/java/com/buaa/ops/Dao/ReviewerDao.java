package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Reviewer;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ReviewerDao {

    Reviewer selectById(Integer id);

    Reviewer selectByAccountId(Integer accountId);

    ArrayList<Reviewer> selectAll();

    Integer insert(Reviewer reviewer);

    Integer update(Reviewer reviewer);

    Integer deleteById(Integer id);
}
