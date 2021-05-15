package com.buaa.ops.Service;

public interface SessionService {
    /**
     * Get account id from session
     * @return AccountId
     */
    Integer getAccountId();

    /**
     * Get author id from session
     * @return Author id or null
     */
    Integer getAuthorId();

    /**
     * Get editor id from session
     * @return Editor id or null
     */
    Integer getEditorId();

    /**
     * Get reviewer id from session
     * @return Reviewer id or null
     */
    Integer getReviewerId();
}
