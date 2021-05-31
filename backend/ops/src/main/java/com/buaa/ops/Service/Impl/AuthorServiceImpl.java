package com.buaa.ops.Service.Impl;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Author;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Service.AuthorService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Override
    public ArrayList<Author> getAuthors() {
        return null;
    }

    @Override
    public Author getAuthorByAuthorId(Integer authorId) {
        return null;
    }

    @Override
    public Author getAuthorByAccountId(Integer accountId) {
        return null;
    }

    @Override
    public void addAuthor(Author author) {

    }

    @Override
    public void removeAuthor(Integer Id) throws ObjectNotFoundException {

    }

    @Override
    public void claimArticle(Integer authorId, Integer articleId) throws RepetitiveOperationException {

    }

    @Override
    public ArrayList<Article> getMyArticles(Integer authorId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void modifyInfos(Author newAuthorInfos) throws ObjectNotFoundException {

    }

    @Override
    public ArrayList<Author> getAuthorsByArticleId(Integer articleId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Review> getReviews(Integer articleId) throws ObjectNotFoundException {
        return null;
    }
}
