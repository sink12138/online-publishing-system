package com.buaa.ops.Service.Impl;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Editor;
import com.buaa.ops.Entity.Write;
import com.buaa.ops.Service.EditorService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EditorServiceImpl implements EditorService {
    @Override
    public Editor getEditorById(Integer id) {
        return null;
    }

    @Override
    public ArrayList<Article> getEditingArticles(Integer editorId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Write> getClaims(Integer editorId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void confirmClaim(Integer articleId, Integer authorId) throws ObjectNotFoundException, RepetitiveOperationException {

    }

    @Override
    public void removeClaim(Integer articleId, Integer authorId) throws ObjectNotFoundException {

    }

    @Override
    public void addEditor(Editor editor) {

    }

    @Override
    public Boolean removeEditor(Integer editorId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Editor> getEditors() {
        return null;
    }

    @Override
    public Editor getEditorByAccountId(Integer accountId) {
        return null;
    }
}
