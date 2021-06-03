package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.*;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Author;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Entity.Write;
import com.buaa.ops.Service.AuthorService;
import com.buaa.ops.Service.Exc.IllegalAuthorityException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private WriteDao writeDao;

    @Override
    public ArrayList<Author> getAuthors() {
        return authorDao.selectAll();
    }

    @Override
    public Author getAuthorByAuthorId(Integer authorId) {
        return authorDao.selectById(authorId);
    }

    @Override
    public Author getAuthorByAccountId(Integer accountId) {
        return authorDao.selectByAccountId(accountId);
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.insert(author);
    }

    @Autowired
    ArticleBufferDao articleBufferDao;

    @Override
    public void removeAuthor(Integer Id) throws ObjectNotFoundException, IllegalAuthorityException {
        ArrayList<Article> articleArrayList = articleDao.selectByAuthorId(Id);
        for (Article article : articleArrayList) {
            if (article.getSubmitterId().equals(Id)) {
                throw new IllegalAuthorityException();
            }
            Write write = new Write(Id, article.getArticleId(), null);
            if (writeDao.deleteBySelf(write) == 0) {
                throw new ObjectNotFoundException();
            }
        }
        articleBufferDao.deleteByAuthorId(Id);
        if (authorDao.deleteById(Id) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void claimArticle(Integer authorId, Integer articleId) throws RepetitiveOperationException {
        Write write = new Write();
        write.setAuthorId(authorId);
        write.setArticleId(articleId);
        if (writeDao.selectBySelf(write) != null)
            throw new RepetitiveOperationException();
        writeDao.insert(write);
    }

    @Override
    public ArrayList<Article> getMyArticles(Integer authorId) throws ObjectNotFoundException {
        if (authorDao.selectById(authorId) == null)
            throw new ObjectNotFoundException();
        return articleDao.selectByAuthorId(authorId);
    }

    @Override
    public void modifyInfos(Author newAuthorInfos) throws ObjectNotFoundException {
        if (authorDao.updateByAccountId(newAuthorInfos) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public ArrayList<Author> getAuthorsByArticleId(Integer articleId) throws ObjectNotFoundException {
        if (articleDao.selectById(articleId) == null)
            throw new ObjectNotFoundException();
        return authorDao.selectByArticleId(articleId);
    }

    @Override
    public ArrayList<Review> getReviews(Integer articleId) throws ObjectNotFoundException {
        if (articleDao.selectById(articleId) == null)
            throw new ObjectNotFoundException();
        return reviewDao.selectByArticleId(articleId);
    }
}
