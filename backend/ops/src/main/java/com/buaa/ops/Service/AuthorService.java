package com.buaa.ops.Service;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Author;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Service.Exc.IllegalAuthorityException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;

import java.util.ArrayList;

/**
 * The interface AuthorService contains services related to authors,
 * namely the operations with authors themselves,
 * and the relationships between authors and articles.
 */
public interface AuthorService {

    /**
     * Used by admin to check all authors
     * @return An instance of ArrayList containing all authors
     */
    ArrayList<Author> getAuthors();

    /**
     * Get an author from table "Author" with the given accountId.
     * @param authorId Id of the target author
     * @return An instance of Author matching the condition,
     * or null if the id does not exist
     */
    Author getAuthorByAuthorId(Integer authorId);

    /**
     * Get an author from table "Author" with the given accountId.
     * This operation includes multi-table queries.
     * @param accountId Id of the target author's account
     * @return An instance of Author matching the condition,
     * or null if the account does not belong to any author
     */
    Author getAuthorByAccountId(Integer accountId);

    /**
     * Add an author into table "Author" and return the distributed authorId.
     * @param author An instance of Author carrying all attributes except AuthorId
     */
    void addAuthor(Author author);

    /**
     * Remove from table "Author" the record having given authorId.
     * This will succeed only if there are currently no articles
     * binding to the author (whether submitted or claimed).
     * @param Id Determines which author to be removed
     */
    void removeAuthor(Integer Id) throws ObjectNotFoundException, IllegalAuthorityException;

    /**
     * Bind one article to an author's account by inserting a new record into table "Write"
     * (the claim is invalid yet until the editor confirms it).
     * @param authorId Determines which author is claiming an article
     * @param articleId Determines which article to be claimed
     * @throws RepetitiveOperationException if the article has already been claimed
     */
    void claimArticle(Integer authorId, Integer articleId) throws RepetitiveOperationException;

    /**
     * Search all articles bound to an author using multi-table queries.
     * The result includes parts as below:<br/>
     * (1) Articles in table "Article" that the author has submitted or claimed,
     * except those being overwritten in table "ArticleBuffer";<br/>
     * (2) Articles in table "ArticleBuffer" that the author as submitted,
     * with a negative articleId as difference.
     * @param authorId Determines whose articles to be queried
     * @return An ArrayList containing all articles bound to the author
     */
    ArrayList<Article> getMyArticles(Integer authorId) throws ObjectNotFoundException;

    /**
     * Modify infos of current author,
     * If the attribute is null, just not modify
     * @param newAuthorInfos An object of Author with modified infos
     */
    void modifyInfos(Author newAuthorInfos) throws ObjectNotFoundException;

    /**
     * Get all author objects who is attached with this article in Table Write
     * @param articleId Target article id
     * @return An ArrayList contains all relative authors
     */
    ArrayList<Author> getAuthorsByArticleId(Integer articleId) throws ObjectNotFoundException;

    /**
     * Get reviews of an article
     * @param articleId Id of article whose reviews you want to check
     * @return An instance of ArrayList containing all reviews of this article
     */
    ArrayList<Review> getReviews(Integer articleId) throws ObjectNotFoundException;
}
