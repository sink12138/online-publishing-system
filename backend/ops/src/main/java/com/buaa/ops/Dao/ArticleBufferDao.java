package com.buaa.ops.Dao;

import com.buaa.ops.Entity.ArticleBuffer;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ArticleBufferDao {
    /**
     * Select one articleBuffer from table "ArticleBuffer".
     * @param id The primary key "article_buffer_id"
     * @return An ArticleBuffer which has the given id
     */
    ArticleBuffer selectById(Integer id);

    ArrayList<ArticleBuffer> selectNeverSubmitted();

    /**
     * Insert a record into table "ArticleBuffer".
     * The "id" attribute of the parameter ArticleBuffer should be null,
     * and will be set to the new primary key after the insertion.
     * @param articleBuffer An instance of ArticleBuffer carrying needed attributes except "id"
     */
    Integer insert(ArticleBuffer articleBuffer);

    /**
     * Update the articleBuffer having given Id in table "ArticleBuffer".
     * Each value got updated only if the related attribute is not null.
     * @param articleBuffer An instance of ArticleBuffer carrying a positive Id
     */
    Integer update(ArticleBuffer articleBuffer);

    /**
     * Delete the articleBuffer having given Id from table "ArticleBuffer".
     * @param Id The primary key "article_buffer_id"
     */
    void deleteById(Integer Id);

}
