package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Author;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface AuthorDao {

    ArrayList<Author> selectAll();

    Author selectById(Integer id);

    Author selectByAccountId(Integer accountId);

    ArrayList<Author> selectByArticleId(Integer articleId);

    Integer insert(Author author);

    Integer deleteById(Integer id);

    /**
     * Null attribute means clearing
     */
    Integer updateByAccountId(Author author);


}
