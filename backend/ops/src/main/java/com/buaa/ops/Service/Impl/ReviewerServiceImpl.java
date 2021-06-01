package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.ArticleDao;
import com.buaa.ops.Dao.ReviewDao;
import com.buaa.ops.Dao.ReviewerDao;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Entity.Reviewer;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import com.buaa.ops.Service.ReviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewerServiceImpl implements ReviewerService {
    @Autowired
    ReviewerDao reviewerDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ReviewDao reviewDao;

    @Override
    public Reviewer getReviewerById(Integer reviewerId) {
        return reviewerDao.selectById(reviewerId);
    }

    @Override
    public void assignReviewers(Integer articleId, ArrayList<Integer> reviewerIdList) throws ObjectNotFoundException, RepetitiveOperationException {
        Article article = articleDao.selectById(articleId);
        if (article == null) {
            throw new ObjectNotFoundException();
        }
        for (Integer reviewerId : reviewerIdList) {
            Review review = new Review(articleId, reviewerId, null, null, null);
            Review anotherReview = reviewDao.selectBySelf(review);
            if (anotherReview != null) {
                throw new RepetitiveOperationException();
            }
            reviewDao.insert(review);
        }
    }

    @Override
    public ArrayList<Article> getReviewingArticles(Integer reviewerId) throws ObjectNotFoundException {
        Reviewer reviewer = reviewerDao.selectById(reviewerId);
        if (reviewer == null) {
            throw new ObjectNotFoundException();
        }
        return articleDao.selectByReviewerId(reviewerId);
    }

    @Override
    public void submitReview(Review review) throws RepetitiveOperationException {
        Review another = new Review(review.getArticleId(), review.getReviewerId(), null, null, null);
        if (reviewDao.selectBySelf(another).getComments() != null) {
            throw new RepetitiveOperationException();
        }
        reviewDao.updateBySelf(review);
     }

    @Override
    public void addReviewer(Reviewer reviewer) {
        reviewerDao.insert(reviewer);
    }

    @Override
    public void removeReviewer(Integer reviewerId) throws ObjectNotFoundException {
        if (reviewerDao.deleteById(reviewerId) == 0) {
            throw new ObjectNotFoundException();
        }
    }

    @Override
    public Reviewer getReviewerByAccountId(Integer accountId) {
        return reviewerDao.selectByAccountId(accountId);
    }

    @Override
    public ArrayList<Reviewer> getReviewersByArticleId(Integer articleId) throws ObjectNotFoundException {
        ArrayList<Reviewer> reviewerArrayList = new ArrayList<>();
        Article article = articleDao.selectById(articleId);
        if (article == null) {
            throw new ObjectNotFoundException();
        }
        ArrayList<Review> reviewArrayList = reviewDao.selectByArticleId(articleId);
        for (Review review : reviewArrayList) {
            reviewerArrayList.add(reviewerDao.selectById(review.getReviewerId()));
        }
        return reviewerArrayList;
    }

    @Override
    public void modifyInfos(Reviewer newReviewerInfos) throws ObjectNotFoundException {
        if (reviewerDao.updateByAccountId(newReviewerInfos) == 0) {
            throw new ObjectNotFoundException();
        }
    }

    @Override
    public ArrayList<Reviewer> getReviewers() {
        return reviewerDao.selectAll();
    }

    @Override
    public Map<String, Boolean> completeReview(Integer articleId) throws ObjectNotFoundException {
        Map<String, Boolean> results = new HashMap<>();
        boolean pass = true;
        Article article = articleDao.selectById(articleId);
        if (article == null) {
            throw new ObjectNotFoundException();
        }
        ArrayList<Review> reviewArrayList = reviewDao.selectByArticleId(articleId);
        for (Review review : reviewArrayList) {
            if (review.getComments() == null) {
                results.put("complete", false);
                return results;
            }
            if (review.getPass().equals(false)) {
                pass = false;
            }
        }
        results.put("complete", true);
        results.put("pass", pass);
        return results;
    }
}
