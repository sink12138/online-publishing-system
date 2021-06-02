package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.ArticleBufferDao;
import com.buaa.ops.Dao.ArticleDao;
import com.buaa.ops.Dao.EditorDao;
import com.buaa.ops.Dao.WriteDao;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Entity.Editor;
import com.buaa.ops.Entity.Write;
import com.buaa.ops.Service.EditorService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EditorServiceImpl implements EditorService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleBufferDao articleBufferDao;

    @Autowired
    private EditorDao editorDao;

    @Autowired
    private WriteDao writeDao;

    @Override
    public Editor getEditorById(Integer id) {
        return editorDao.selectById(id);
    }

    @Override
    public ArrayList<Article> getEditingArticles(Integer editorId) throws ObjectNotFoundException {
        if (editorDao.selectById(editorId) == null)
            throw new ObjectNotFoundException();
        return articleDao.selectByEditorId(editorId);
    }

    @Override
    public ArrayList<Write> getClaims(Integer editorId) throws ObjectNotFoundException {
        if (editorDao.selectById(editorId) == null)
            throw new ObjectNotFoundException();
        return writeDao.selectByEditorId(editorId);
    }

    @Override
    public void confirmClaim(Integer articleId, Integer authorId) throws ObjectNotFoundException, RepetitiveOperationException {
        Write write = new Write();
        write.setArticleId(articleId);
        write.setAuthorId(authorId);
        Write updateWrite = writeDao.selectBySelf(write);
        if (updateWrite == null)
            throw new ObjectNotFoundException();
        boolean confirmed = updateWrite.getConfirmed();
        if (confirmed)
            throw new RepetitiveOperationException();
        updateWrite.setConfirmed(true);
        if (writeDao.updateBySelf(updateWrite) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void removeClaim(Integer articleId, Integer authorId) throws ObjectNotFoundException {
        Write write = new Write();
        write.setArticleId(articleId);
        write.setAuthorId(authorId);
        if (writeDao.deleteBySelf(write) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void addEditor(Editor editor) {
        editorDao.insert(editor);
    }

    @Override
    public Boolean removeEditor(Integer editorId) throws ObjectNotFoundException {
        ArrayList<Editor> twoEditors = editorDao.selectTwoLeastBusy();
        if (twoEditors.size() == 1)
            return false;
        Integer alternateId;
        Integer leastBusyId = twoEditors.get(0).getEditorId();
        if (leastBusyId.equals(editorId))
            // The editor to be removed is the least busy editor
            alternateId = twoEditors.get(1).getEditorId();
        else {
            alternateId = leastBusyId;
        }
        ArrayList<Article> articles = articleDao.selectByEditorId(editorId);
        int success = 0;
        for (Article a : articles) {
            Integer articleId = a.getArticleId();
            if (articleId < 0) {
                ArticleBuffer articleBuffer = new ArticleBuffer();
                articleBuffer.setArticleBufferId(articleId);
                articleBuffer.setEditorId(alternateId);
                success += articleBufferDao.updateById(articleBuffer);
            }
            else {
                Article article = new Article();
                article.setArticleId(articleId);
                article.setEditorId(alternateId);
                success += articleDao.updateById(article);
            }
            if (success < articles.size())
                return false;
        }
        if (editorDao.deleteById(editorId) == 0)
            throw new ObjectNotFoundException();
        return true;
    }

    @Override
    public ArrayList<Editor> getEditors() {
        return editorDao.selectAll();
    }

    @Override
    public Editor getEditorByAccountId(Integer accountId) {
        return editorDao.selectByAccountId(accountId);
    }
}
