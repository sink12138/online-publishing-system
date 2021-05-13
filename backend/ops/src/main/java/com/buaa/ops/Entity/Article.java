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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;

    private String title;
    private String identifier;
    private String keywords;
    private String articleAbstract;
    private String file_path;
    private String first_author;
    private String other_author;
    private int submitterId;
    private int editorId;
    private String status;
    private java.sql.Date publishingDate;

    public Article() {

    }

    public Article(String title, String identifier, String keywords, String articleAbstract, String file_path,
                   String first_author, String other_author, int submitterId, int editorId,
                   String status, java.sql.Date publishingDate) {
        this.title = title;
        this.identifier = identifier;
        this.keywords = keywords;
        this.articleAbstract = articleAbstract;
        this.file_path = file_path;
        this.first_author = first_author;
        this.other_author = other_author;
        this.submitterId = submitterId;
        this.editorId = editorId;
        this.status = status;
        this.publishingDate = publishingDate;
    }
}
