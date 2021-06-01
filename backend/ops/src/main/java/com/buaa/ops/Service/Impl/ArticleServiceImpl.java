package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.ArticleBufferDao;
import com.buaa.ops.Dao.ArticleDao;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Service.ArticleService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleBufferDao articleBufferDao;

    @Override
    public ArrayList<Article> searchPublishedArticles(String searchType, String searchString) {
        return null;
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleDao.selectById(id);
    }

    @Override
    public ArticleBuffer getArticleBufferById(Integer id) {
        return articleBufferDao.selectById(id);
    }

    @Override
    public File getArticleFile(Integer id) throws ObjectNotFoundException {
        Article article = articleDao.selectById(id);
        if (article == null)
            throw new ObjectNotFoundException();
        String path = article.getFilePath();
        if (path == null)
            throw new ObjectNotFoundException();
        File file = new File(path);
        if (!file.exists())
            throw new ObjectNotFoundException();
        return file;
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
        Article article = new Article();
        article.setArticleId(articleId);
        article.setStatus(status);
        if (articleDao.update(article) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void removeArticle(Integer id) throws ObjectNotFoundException {
        if (articleDao.deleteById(id) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void rejectArticle(Integer articleBufferId) {
        articleBufferDao.deleteById(articleBufferId);
    }

    @Override
    public void saveEditedFile(Integer articleId, MultipartFile file) throws IOException, ObjectNotFoundException {

    }

    @Override
    public void publishArticle(Integer articleId, String identifier) throws ObjectNotFoundException, RepetitiveOperationException {
        Article article = articleDao.selectById(articleId);
        if (article == null)
            throw new ObjectNotFoundException();
        if (article.getStatus().equals("已出版"))
            throw new RepetitiveOperationException();
        Article updateArticle = new Article();
        updateArticle.setArticleId(articleId);
        updateArticle.setIdentifier(identifier);
        updateArticle.setStatus("已出版");
        updateArticle.setPublishingDate(new java.sql.Date(new java.util.Date().getTime()));
        if (articleDao.update(updateArticle) == 0)
            throw new ObjectNotFoundException();
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
