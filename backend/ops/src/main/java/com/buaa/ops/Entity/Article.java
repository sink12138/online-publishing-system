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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;

    private String title;
    private String identifier;
    private String keywords;
    private String articleAbstract;
    private String filePath;
    private String firstAuthor;
    private String otherAuthors;
    private Integer submitterId;
    private Integer editorId;
    private String status;
    private java.sql.Date publishingDate;

    public Article() {

    }

    public Article(String title, String identifier, String keywords, String articleAbstract, String filePath,
                   String firstAuthor, String otherAuthors, Integer submitterId, Integer editorId,
                   String status, java.sql.Date publishingDate) {
        this.title = title;
        this.identifier = identifier;
        this.keywords = keywords;
        this.articleAbstract = articleAbstract;
        this.filePath = filePath;
        this.firstAuthor = firstAuthor;
        this.otherAuthors = otherAuthors;
        this.submitterId = submitterId;
        this.editorId = editorId;
        this.status = status;
        this.publishingDate = publishingDate;
    }
}
