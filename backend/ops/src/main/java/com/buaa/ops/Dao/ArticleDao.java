package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ArticleDao {

    Article selectById(Integer id);

    ArrayList<Article> selectByAuthorId(Integer authorId);

    ArrayList<Article> selectByReviewerId(Integer reviewerId);

    ArrayList<Article> selectByEditorId(Integer editorId);

    ArrayList<Article> selectPublishedByTitle(String subtitle);

    ArrayList<Article> selectPublishedByAuthor(String author);

    ArrayList<Article> selectPublishedByKeyword(String keyword);

    ArrayList<Article> selectAll();

    Integer insert(Article article);

    /**
     * Null attribute = do not updateById
     */
    Integer updateById(Article article);

    Integer deleteById(Integer id);

}
