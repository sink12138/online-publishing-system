package com.buaa.ops.Service;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The interface ArticleService contains services dealing with only articles,
 * namely the operations with article files and article records in the database.
 */
public interface ArticleService {
    /**
     * Search all published articles matching given condition from the database.
     * @param searchType Type of the condition: "title", "author" or "keyword"
     * @param searchString Content of the condition:
     *                     substring of a title,
     *                     name of an author
     *                     or one keyword
     * @return An ArrayList containing all fitting articles
     */
    ArrayList<Article> searchPublishedArticles(String searchType,
                                               String searchString);

    /**
     * Get an article with the specific articleId.
     * @param id The required articleId
     * @return An article with given articleId
     */
    Article getArticleById(Integer id);

    /**
     * Get an articleBuffer with the specific articleBufferId.
     * @param id The required articleBufferId
     * @return An articleBuffer with given articleBufferId
     */
    ArticleBuffer getArticleBufferById(Integer id);

    /**
     * Get original file of an article with specific articleId.
     * @param id The required articleId
     * @return An instance of java.io.File referring to the path of the original article file
     */
    File getArticleFile(Integer id) throws IOException, ObjectNotFoundException;

    /**
     * Save the uploaded article file on the server, including steps as below:<br/>
     * (1) Save the original file to the storage,
     * or replace the former file if it exists;<br/>
     * (2) Insert a new record into table "ArticleBuffer",
     * or updateById the old record if it exists;<br/>
     * (3) Return the newly generated articleBufferId if it is a new upload,
     * or return the given articleBufferId if it is a replacement.
     * @param submitterId The authorId of the submitter (uploader) of the file
     * @param articleBufferId The primary key of ArticleBuffer (null if it is a new upload)
     * @param overwrite Determines which article in table "Article" this upload is going to
     *                  overwrite (null if it is a new submission)
     * @param file An instance of MultipartFile (uploaded file)
     * @return The articleBufferId related (or distributed) to this upload
     */
    Integer saveArticleFile(Integer submitterId,
                            Integer articleBufferId,
                            Integer overwrite,
                            MultipartFile file) throws IOException;

    /**
     * Submit information of an article into table "ArticleBuffer" after the file is uploaded.
     * @param articleBuffer The articleBuffer carrying title, abstract, keyword, authors, etc.
     *                      (articleBufferId required)
     * @return The distributed (new submission) or related (revised draft) editorId
     * to the submitted article
     */
    Integer submitArticle(ArticleBuffer articleBuffer) throws ObjectNotFoundException;

    /**
     * Move a record from table "ArticleBuffer" to table "Article".<br/>
     * Insert new record if it is a new article,
     * or updateById the old record if it is a revised draft.
     * @param articleBufferId Id of the ArticleBuffer to be moved to table "Article"
     * @return The articleId related (or distributed) to this operation
     */
    Integer moveToArticle(Integer articleBufferId);

    /**
     * Change the status to an article in table "Article"
     * @param articleId Determines which record to updateById
     * @param status The new status to be set to the article
     */
    void setArticleStatus(Integer articleId, String status) throws ObjectNotFoundException;

    /**
     * Remove one article, along with all its related information from the database.
     * @param id ArticleId of the article to be deleted
     * @throws ObjectNotFoundException if there is no such article
     */
    void removeArticle(Integer id) throws ObjectNotFoundException;

    /**
     * Reject an article in table "ArticleBuffer" whether it is a new article or a revised draft.
     * @param articleBufferId Determines which article to reject
     */
    void rejectArticle(Integer articleBufferId);

    /**
     * Save the article file the editor uploads after editing,
     * including replacement of the storage and updates in the database.
     * @param articleId Determines which article to updateById
     * @param file An instance of MultipartFile (uploaded file)
     */
    void saveEditedFile(Integer articleId, MultipartFile file) throws IOException, ObjectNotFoundException;

    /**
     * Publish an article in the database,
     * including changing its status and setting its identifier.
     * @param articleId Determines which article to publish
     * @param identifier The unique identifier given to the article
     * @throws RepetitiveOperationException If the article has been published
     */
    void publishArticle(Integer articleId, String identifier) throws ObjectNotFoundException, RepetitiveOperationException;

    /**
     * Clean all article garbage from the storage and the database.<br/>
     * Garbage: (1) files uploaded but never submitted;
     * (2) records in table "ArticleBuffer" with everything null except id and file path.
     * @return True if all garbage is successfully cleaned, or false if not
     */
    Boolean cleanBuffer();

    /**
     * Used by admin to check all the articles
     * @return An instance of ArrayList containing all the articles currently
     */
    ArrayList<Article> getArticles();

    /**
     * Clear all reviews of an article from table "Review".
     * @param articleId Determines whose reviews to be cleared
     */
    void clearReviews(Integer articleId) throws ObjectNotFoundException;

    /**
     * Remove all reviews of an article from table "Review".
     * @param articleId Determines whose reviews to be removed
     */
    void removeReviews(Integer articleId) throws ObjectNotFoundException;

}
