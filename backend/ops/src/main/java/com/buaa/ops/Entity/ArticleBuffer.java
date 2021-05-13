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
public class ArticleBuffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleBufferId;

    private String title;
    private String keywords;
    private String articleAbstract;
    private String file_path;
    private String first_author;
    private String other_author;
    private int submitterId;
    private int editorId;

    public ArticleBuffer() {

    }

    public ArticleBuffer(String title, String keywords, String articleAbstract,
                         String file_path, String first_author, String other_author, int submitterId, int editorId) {
        this.title = title;
        this.keywords = keywords;
        this.articleAbstract = articleAbstract;
        this.file_path = file_path;
        this.first_author = first_author;
        this.other_author = other_author;
        this.submitterId = submitterId;
        this.editorId = editorId;
    }
}