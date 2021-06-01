package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.ArticleBufferDao;
import com.buaa.ops.Dao.ArticleDao;
import com.buaa.ops.Dao.EditorDao;
import com.buaa.ops.Dao.ReviewDao;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Entity.Editor;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Service.ArticleService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleBufferDao articleBufferDao;

    @Autowired
    private EditorDao editorDao;

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public ArrayList<Article> searchPublishedArticles(String searchType, String searchString) {
        ArrayList<Article> results;
        switch (searchType) {
            case "title":
                results = articleDao.selectPublishedByTitle(searchString);
                break;
            case "author":
                results = articleDao.selectPublishedByAuthor(searchString);
                break;
            case "keyword":
                results = articleDao.selectPublishedByKeyword(searchString);
                break;
            default:
                return null;
        }
        return results;
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
        // TODO: 2021/6/1 Save the file and insert a new record into database; or replace old file and update records in database
        return null;
    }

    @Override
    public Integer submitArticle(ArticleBuffer articleBuffer) throws ObjectNotFoundException {
        Integer overwrite = articleBuffer.getOverwrite();
        Integer editorId;
        if (overwrite == null) { // New submission
            Editor targetEditor = editorDao.selectLeastBusy();
            if (targetEditor == null)
                throw new ObjectNotFoundException();
            editorId = targetEditor.getEditorId();
        }
        else {
            Article article = articleDao.selectById(overwrite);
            if (article == null)
                throw new ObjectNotFoundException();
            editorId = article.getEditorId();
        }
        articleBuffer.setEditorId(editorId);
        if (articleBufferDao.updateById(articleBuffer) == 0)
            throw new ObjectNotFoundException();
        return editorId;
    }

    @Override
    public Integer moveToArticle(Integer articleBufferId) {
        ArticleBuffer articleBuffer = articleBufferDao.selectById(articleBufferId);
        // TODO: 2021/6/1 File copy and record immigration
        return null;
    }

    @Override
    public void setArticleStatus(Integer articleId, String status) throws ObjectNotFoundException {
        Article article = new Article();
        article.setArticleId(articleId);
        article.setStatus(status);
        if (articleDao.updateById(article) == 0)
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
        // TODO: 2021/6/1 Replace the old file with the new file
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
        if (articleDao.updateById(updateArticle) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public Boolean cleanBuffer() {
        return null;
    }

    @Override
    public ArrayList<Article> getArticles() {
        return articleDao.selectAll();
    }

    @Override
    public void clearReviews(Integer articleId) throws ObjectNotFoundException {
        ArrayList<Review> reviews = reviewDao.selectByArticleId(articleId);
        if (reviews.isEmpty())
            throw new ObjectNotFoundException();
        int success = 0;
        for (Review r : reviews) {
            Review review = new Review();
            review.setArticleId(r.getArticleId());
            review.setArticleId(r.getArticleId());
            success += reviewDao.updateBySelf(review);
        }
        if (success != reviews.size())
            throw new ObjectNotFoundException();
    }

    @Override
    public void removeReviews(Integer articleId) throws ObjectNotFoundException {
        ArrayList<Review> reviews = reviewDao.selectByArticleId(articleId);
        if (reviewDao.deleteByArticleId(articleId) < reviews.size())
            throw new ObjectNotFoundException();
    }
}
