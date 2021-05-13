package com.buaa.ops.Dao;

import com.buaa.ops.Entity.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ArticleDao {
    /**
     * Select the exact article by Id.
     * @param Id The primary key 'article_id' of table Article
     * @return An Article which has the given Id.
     */
    Article selectArticleById(int Id);

    /**
     * Select all published articles from the database whose titles contain given subtitle.
     * @param subtitle A String given to select articles.
     * @return An ArrayList which contains all articles selected.
     */
    ArrayList<Article> selectPublishedArticlesByTitle(String subtitle);

    /**
     * Select all published articles from the database which have the given author.
     * @param author A String given as the target author.
     * @return An ArrayList which contains all articles selected.
     */
    ArrayList<Article> selectPublishedArticlesByAuthor(String author);

    /**
     * Select all published articles from the database which have the given keyword.
     * @param keyword A String given as the target keyword.
     * @return An ArrayList which contains all articles selected.
     */
    ArrayList<Article> selectArticlesByKeyword(String keyword);
}
