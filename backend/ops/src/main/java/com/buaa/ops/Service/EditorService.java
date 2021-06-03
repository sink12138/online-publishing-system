package com.buaa.ops.Service;

import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;

import java.util.ArrayList;

public interface EditorService {

    /**
     * Get an editor with the specific editorId.
     * @param id The required editorId
     * @return An instance of Editor with given editorId
     */
    Editor getEditorById(Integer id);

    /**
     * Get all the articles to be processed by current editor.
     * The results includes parts as below:<br/>
     * (1) Articles in table "Article" related to the editor,
     * except those being overwritten in table "ArticleBuffer";<br/>
     * (2) Articles in table "ArticleBuffer" related to the editor,
     * with a negative articleId as difference.
     * @param editorId Id of current editor
     * @return An object of ArrayList contains all the articles to be processed
     */
    ArrayList<Article> getEditingArticles(Integer editorId) throws ObjectNotFoundException;

    /**
     * Get all claims of articles related to a specific editor.
     * This operation includes multi-table queries, and returns records from table "Write"
     * which satisfy conditions below:<br/>
     * (1) The articleId refers to an article whose editor matches the given editorId;<br/>
     * (2) The authorId refers to an author who is not the submitter of the article
     * (since submitters do not need to claim their articles by themselves)
     * @param editorId Determines a specific editor
     * @return Results of the query, or null if nothing is selected
     */
    ArrayList<Write> getClaims(Integer editorId) throws ObjectNotFoundException;

    /**
     * Used by editor to confirm claim request by author,
     * Update appropriate Write object, set confirmed attribute to true
     * @param articleId Id of article claimed
     * @param authorId Id of author
     */
    void confirmClaim(Integer articleId, Integer authorId) throws ObjectNotFoundException, RepetitiveOperationException;

    /**
     * Used by editor to reject claim request by author,
     * Delete appropriate Write object,
     * Can't remove if the author is submitter
     * @param articleId Id of article claimed
     * @param authorId Id of author
     */
    void removeClaim(Integer articleId, Integer authorId) throws ObjectNotFoundException, RepetitiveOperationException;

    /**
     * Used by Admin to add a new editor
     * @param editor A new object of Editor
     */
    void addEditor(Editor editor) ;

    /**
     * Remove appropriate Editor by id, but not delete his/her Account,
     * At the same time assign a new editor to replace,
     * Can't remove if he/she is the last editor
     * @param editorId Id of target editor
     * @return Whether success
     * @throws ObjectNotFoundException If id doesn't exist
     */
    Boolean removeEditor(Integer editorId) throws ObjectNotFoundException;

    /**
     * Used by Admin to check all the editors
     * @return An instance of ArrayList containing all the editors currently
     */
    ArrayList<Editor> getEditors();

    /**
     * Get an author from table "Editor" with the given accountId.
     * This operation includes multi-table queries.
     * @param accountId Id of the target editor's account
     * @return An instance of Editor matching the condition,
     * or null if the account does not belong to any editor
     */
    Editor getEditorByAccountId(Integer accountId);

}
