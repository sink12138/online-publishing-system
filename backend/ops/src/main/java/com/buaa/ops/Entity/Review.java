package com.buaa.ops.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private Integer articleId;
    private Integer reviewerId;
    private String comments;
    private Boolean pass;
    private java.sql.Date reviewingDate;

    public Review() {
    }

    public Review(Integer articleId, Integer reviewerId, String comments, Boolean pass, java.sql.Date reviewingDate) {
        this.articleId = articleId;
        this.reviewerId = reviewerId;
        this.comments = comments;
        this.pass = pass;
        this.reviewingDate = reviewingDate;
    }
}
