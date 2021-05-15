package com.buaa.ops.Service;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Author;

import java.util.ArrayList;

/**
 * The interface AuthorService contains services related to authors,
 * namely the operations with authors themselves,
 * and the relationships between authors and articles.
 */
public interface AuthorService {
    /**
     * Add an author into table "Author" and return the distributed authorId.
     * @param author An instance of Author carrying all attributes except AuthorId
     * @return The new authorId generated by the database, or null if the operation failed
     */
    Integer addAuthor(Author author);

    /**
     * Remove from table "Author" the record having given authorId.
     * This will succeed only if there are currently no articles
     * binding to the author (whether submitted or claimed).
     * @param Id Determines which author to be removed
     * @return True if this operation succeeded, or false if not
     */
    Boolean removeAuthor(Integer Id);

    /**
     * Bind one article to an author's account by inserting a new record into table "Write"
     * (the claim is invalid yet until the editor confirms it).
     * @param authorId Determines which author is claiming an article
     * @param articleId Determines which article to be claimed
     * @return True if this operation succeeded, or false if not
     */
    Boolean claimArticle(Integer authorId, Integer articleId);

    /**
     * Search all articles bound to an author using multi-table queries.
     * @param authorId Determines whose articles to be queried
     * @return An ArrayList containing all articles bound to the author
     */
    ArrayList<Article> getMyArticles(Integer authorId);
}