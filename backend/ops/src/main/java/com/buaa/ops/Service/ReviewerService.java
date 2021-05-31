package com.buaa.ops.Service;

import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;

import java.util.ArrayList;
import java.util.Map;

/**
 * The interface ReviewerService contains services related to reviewers,
 * including relations among reviewers, reviews, articles.
 */
public interface ReviewerService {

    /**
     * Get reviewer by id
     * @param reviewerId Target id
     * @return An instance of Reviewer with this id
     */
    Reviewer getReviewerById(Integer reviewerId);

    /**
     * Assign reviewers for an article. This will insert several records into table "Review".
     * @param articleId Determines which article to assign to
     * @param reviewerIdList An ArrayList containing all reviewerId of target reviewers
     * @throws RepetitiveOperationException If the the reviewer has been assigned for this article
     */
    void assignReviewers(Integer articleId, ArrayList<Integer> reviewerIdList) throws ObjectNotFoundException, RepetitiveOperationException;

    /**
     * Get all articles one reviewer is reviewing, or has reviewed.
     * @param reviewerId Determines whose related articles to be queried
     * @return An ArrayList containing all related articles of the reviewer
     */
    ArrayList<Article> getReviewingArticles(Integer reviewerId) throws ObjectNotFoundException;

    /**
     * Submit the review of a reviewer to an article
     * @param review An instance of Review carrying all attributes except reviewId
     */
    void submitReview(Review review) throws RepetitiveOperationException;

    /**
     * Add a new reviewer into the database,
     * The reviewer should have registered an account (accountId != null).
     * @param reviewer An instance of Reviewer carrying all attributes except reviewerId
     */
    void addReviewer(Reviewer reviewer);

    /**
     * Remove a reviewer from the database. This operation will succeed only if the,
     * Target reviewer is not reviewing any article (in other words, the reviewer is not,
     * Currently responsible for any article which has not been finalized).
     * @param reviewerId Determines which reviewer to be removed
     * @throws ObjectNotFoundException If id doesn't exist
     */
    void removeReviewer(Integer reviewerId) throws ObjectNotFoundException;

    /**
     * Get an author from table "Reviewer" with the given accountId.
     * This operation includes multi-table queries.
     * @param accountId Id of the target reviewer's account
     * @return An instance of Reviewer matching the condition,
     * or null if the account does not belong to any reviewer
     */
    Reviewer getReviewerByAccountId(Integer accountId);

    /**
     * Get all reviewer objects who is attached with this article in Table Write
     * @param articleId Target article id
     * @return An ArrayList contains all relative reviewers
     */
    ArrayList<Reviewer> getReviewersByArticleId(Integer articleId) throws ObjectNotFoundException;

    /**
     * Modify infos of current reviewer,
     * If the attribute is null, just not modify
     * @param newReviewerInfos An object of Reviewer with modified infos
     */
    void modifyInfos(Reviewer newReviewerInfos) throws ObjectNotFoundException;

    /**
     * Used by Admin to check all the reviewers
     * @return An instance of ArrayList containing all the reviewers currently
     */
    ArrayList<Reviewer> getReviewers();

    /**
     * Used　by Reviewer to judge whether all the reviewers have submitted review to this article
     * @param articleId Id of target article
     * @return An instance of Map, the first element(key is "complete") stands for whether complete review, if completed, return true,<br>
     * and the second (key is "pass") stands for whether pass, if pass, return true<br/>
     */
    Map<String, Boolean> completeReview(Integer articleId) throws ObjectNotFoundException;

}
