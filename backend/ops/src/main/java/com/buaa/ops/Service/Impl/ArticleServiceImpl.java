package com.buaa.ops.Service.Impl;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Service.ArticleService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Override
    public ArrayList<Article> searchPublishedArticles(String searchType, String searchString) {
        return null;
    }

    @Override
    public Article getArticleById(Integer id) {
        return null;
    }

    @Override
    public ArticleBuffer getArticleBufferById(Integer id) {
        return null;
    }

    @Override
    public File getArticleFile(Integer id) throws IOException, ObjectNotFoundException {
        return null;
    }

    @Override
    public Integer saveArticleFile(Integer submitterId, Integer articleBufferId, Integer overwrite, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public Integer submitArticle(ArticleBuffer articleBuffer) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public Integer moveToArticle(Integer articleBufferId) {
        return null;
    }

    @Override
    public void setArticleStatus(Integer articleId, String status) throws ObjectNotFoundException {

    }

    @Override
    public void removeArticle(Integer id) throws ObjectNotFoundException {

    }

    @Override
    public void rejectArticle(Integer articleBufferId) {

    }

    @Override
    public void saveEditedFile(Integer articleId, MultipartFile file) throws IOException, ObjectNotFoundException {

    }

    @Override
    public void publishArticle(Integer articleId, String identifier) throws ObjectNotFoundException, RepetitiveOperationException {

    }

    @Override
    public Boolean cleanBuffer() {
        return null;
    }

    @Override
    public ArrayList<Article> getArticles() {
        return null;
    }

    @Override
    public void clearReviews(Integer articleId) throws ObjectNotFoundException {

    }

    @Override
    public void removeReviews(Integer articleId) throws ObjectNotFoundException {

    }
}
