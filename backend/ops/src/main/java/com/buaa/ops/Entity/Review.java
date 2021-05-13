package com.buaa.ops.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    private int articleId;
    private int reviewerId;
    private String comments;
    private boolean pass;
    private java.sql.Date reviewingDate;

    public Review() {
    }

    public Review(int articleId, int reviewerId, String comments, boolean pass, java.sql.Date reviewingDate) {
        this.articleId = articleId;
        this.reviewerId = reviewerId;
        this.comments = comments;
        this.pass = pass;
        this.reviewingDate = reviewingDate;
    }
}
