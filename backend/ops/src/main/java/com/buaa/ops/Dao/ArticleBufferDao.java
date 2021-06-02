package com.buaa.ops.Dao;

import com.buaa.ops.Entity.ArticleBuffer;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ArticleBufferDao {

    ArticleBuffer selectById(Integer id);

    ArrayList<ArticleBuffer> selectNeverSubmitted();

    Integer insert(ArticleBuffer articleBuffer);

    /**
     * Null attribute = do not update
     */
    Integer updateById(ArticleBuffer articleBuffer);

    Integer deleteById(Integer id);

    Integer deleteByAuthorId(Integer authorId);

}
