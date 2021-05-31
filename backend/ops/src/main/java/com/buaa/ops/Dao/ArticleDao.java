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
    /**
     * Select one article from table "Article".
     * @param Id The primary key "article_id"
     * @return An Article which has the given Id
     */
    Article selectById(Integer Id);

    ArrayList<Article> selectByAuthorId(Integer authorId);

    ArrayList<Article> selectByReviewerId(Integer reviewerId);

    ArrayList<Article> selectByEditorId(Integer editorId);

    /**
     * Select all published articles from the database whose titles contain given subtitle.
     * @param subtitle A String given to select articles
     * @return An ArrayList which contains all articles selected
     */
    ArrayList<Article> selectPublishedByTitle(String subtitle);

    /**
     * Select all published articles from the database which have the given author.
     * @param author A String given as the target author
     * @return An ArrayList which contains all articles selected
     */
    ArrayList<Article> selectPublishedByAuthor(String author);

    /**
     * Select all published articles from the database which have the given keyword.
     * @param keyword A String given as the target keyword
     * @return An ArrayList which contains all articles selected
     */
    ArrayList<Article> selectPublishedByKeyword(String keyword);

    ArrayList<Article> selectAll();

    /**
     * Insert a record into table "Article".
     * The "id" attribute of the parameter Article should be null,
     * and will be set to the new primary key after the insertion.
     * @param article An instance of Article carrying needed attributes except "id"
     * @return True if the insertion succeeded, or false if not.
     */
    Boolean insert(Article article);

    /**
     * Update the article having given Id in table "Article".
     * Each value got updated only if the related attribute is not null.
     * @param article An instance of Article carrying a positive Id
     */
    Integer update(Article article);

    /**
     * Delete the article having given Id from table "Article".
     * @param id The primary key "article_id"
     */
    Integer deleteById(Integer id);

}
