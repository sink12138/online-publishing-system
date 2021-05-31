package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * Contains selections, insertions, deletions and updates
 * ONLY related to table "Article" and "ArticleBuffer" in the database.
 */
@Mapper
public interface ArticleDao {

    Article selectById(Integer Id);

    ArrayList<Article> selectByAuthorId(Integer authorId);

    ArrayList<Article> selectByReviewerId(Integer reviewerId);

    ArrayList<Article> selectByEditorId(Integer editorId);

    ArrayList<Article> selectPublishedByTitle(String subtitle);

    ArrayList<Article> selectPublishedByAuthor(String author);

    ArrayList<Article> selectPublishedByKeyword(String keyword);

    ArrayList<Article> selectAll();

    Integer insert(Article article);

    /**
     * Null attribute = do not update
     */
    Integer update(Article article);

    Integer deleteById(Integer id);

}
