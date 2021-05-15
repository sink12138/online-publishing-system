package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * Contains selections, insertions, deletions and updates
 * ONLY related to table "Article" and "ArticleBuffer" in the database.
 */
@Mapper
public interface ArticleDao {
    /**
     * Select one article from table "Article".
     * @param Id The primary key "article_id"
     * @return An Article which has the given Id
     */
    Article selectArticleById(Integer Id);

    /**
     * Select one articleBuffer from table "ArticleBuffer".
     * @param Id The primary key "article_buffer_id"
     * @return An ArticleBuffer which has the given Id
     */
    ArticleBuffer selectArticleBufferById(Integer Id);

    /**
     * Select all published articles from the database whose titles contain given subtitle.
     * @param subtitle A String given to select articles
     * @return An ArrayList which contains all articles selected
     */
    ArrayList<Article> selectPublishedArticlesByTitle(String subtitle);

    /**
     * Select all published articles from the database which have the given author.
     * @param author A String given as the target author
     * @return An ArrayList which contains all articles selected
     */
    ArrayList<Article> selectPublishedArticlesByAuthor(String author);

    /**
     * Select all published articles from the database which have the given keyword.
     * @param keyword A String given as the target keyword
     * @return An ArrayList which contains all articles selected
     */
    ArrayList<Article> selectArticlesByKeyword(String keyword);

    /**
     * Insert a record into table "Article".
     * The "id" attribute of the parameter Article should be null,
     * and will be set to the new primary key after the insertion.
     * @param article An instance of Article carrying needed attributes except "id"
     * @return True if the insertion succeeded, or false if not.
     */
    Boolean insertArticle(Article article);

    /**
     * Insert a record into table "ArticleBuffer".
     * The "id" attribute of the parameter ArticleBuffer should be null,
     * and will be set to the new primary key after the insertion.
     * @param articleBuffer An instance of ArticleBuffer carrying needed attributes except "id"
     */
    void insertArticleBuffer(ArticleBuffer articleBuffer);

    /**
     * Delete the article having given Id from table "Article".
     * @param Id The primary key "article_id"
     */
    void deleteArticle(Integer Id);

    /**
     * Delete the articleBuffer having given Id from table "ArticleBuffer".
     * @param Id The primary key "article_buffer_id"
     */
    void deleteArticleBuffer(Integer Id);

    /**
     * Update the article having given Id in table "Article".
     * Each value got updated only if the related attribute is not null.
     * @param article An instance of Article carrying a positive Id
     */
    void updateArticle(Article article);

    /**
     * Update the articleBuffer having given Id in table "ArticleBuffer".
     * Each value got updated only if the related attribute is not null.
     * @param articleBuffer An instance of ArticleBuffer carrying a positive Id
     */
    void updateArticleBuffer(ArticleBuffer articleBuffer);

}
