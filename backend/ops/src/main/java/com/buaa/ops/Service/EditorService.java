package com.buaa.ops.Service;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Editor;

import java.util.ArrayList;

public interface EditorService {
    /**
     * Get all the articles to be processed by current editor
     * @param editorId Id of current editor
     * @return An object of ArrayList contains all the articles to be processed
     */
    ArrayList<Article> getEditingArticles(Integer editorId);

    /**
     * Used by editor to confirm claim request by author,
     * Update appropriate Write object, set confirmed attribute to true
     * @param articleId Id of article claimed
     * @param authorId Id of author
     * @return Whether success
     */
    Boolean confirmClaim(Integer articleId, Integer authorId);

    /**
     * Used by editor to reject claim request by author,
     * Delete appropriate Write object,
     * Can't remove if the author is submitter
     * @param articleId Id of article claimed
     * @param authorId Id of author
     * @return Whether success
     */
    Boolean removeClaim(Integer articleId, Integer authorId);

    /**
     * Used by Admin to add a new editor
     * @param editor A new object of Editor
     * @return Whether success
     */
    Boolean addEditor(Editor editor);

    /**
     * Remove appropriate Editor by id, but not delete his/her Account,
     * At the same time assign a new editor to replace,
     * Can't remove if he/she is the last editor
     * @param editorId Id of target editor
     * @return Whether success
     */
    Boolean removeEditor(Integer editorId);

    /**
     * Used by Admin to check all the editors
     * @return All the editors currently
     */
    ArrayList<Editor> getEditors();
}
