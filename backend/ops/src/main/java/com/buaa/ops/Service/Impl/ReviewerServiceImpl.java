package com.buaa.ops.Service.Impl;

import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Entity.Reviewer;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import com.buaa.ops.Service.ReviewerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    @Override
    public Reviewer getReviewerById(Integer reviewerId) {
        return null;
    }

    @Override
    public void assignReviewers(Integer articleId, ArrayList<Integer> reviewerIdList) throws ObjectNotFoundException, RepetitiveOperationException {

    }

    @Override
    public ArrayList<Article> getReviewingArticles(Integer reviewerId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void submitReview(Review review) throws RepetitiveOperationException {

    }

    @Override
    public void addReviewer(Reviewer reviewer) {

    }

    @Override
    public void removeReviewer(Integer reviewerId) throws ObjectNotFoundException {

    }

    @Override
    public Reviewer getReviewerByAccountId(Integer accountId) {
        return null;
    }

    @Override
    public ArrayList<Reviewer> getReviewersByArticleId(Integer articleId) throws ObjectNotFoundException {
        return null;
    }

    @Override
    public void modifyInfos(Reviewer newReviewerInfos) throws ObjectNotFoundException {

    }

    @Override
    public ArrayList<Reviewer> getReviewers() {
        return null;
    }

    @Override
    public Map<String, Boolean> completeReview(Integer articleId) throws ObjectNotFoundException {
        return null;
    }
}
