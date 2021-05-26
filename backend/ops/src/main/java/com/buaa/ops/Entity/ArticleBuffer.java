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
    private Integer articleBufferId;

    private String title;
    private String keywords;
    private String articleAbstract;
    private String filePath;
    private String firstAuthor;
    private String otherAuthor;
    private Integer submitterId;
    private Integer editorId;
    private Integer overwrite;

    public ArticleBuffer() {

    }

    public ArticleBuffer(Integer articleBufferId, String title, String keywords, String articleAbstract, String firstAuthor, String otherAuthor) {
        this.articleBufferId = articleBufferId;
        this.title = title;
        this.keywords = keywords;
        this.articleAbstract = articleAbstract;
        this.firstAuthor = firstAuthor;
        this.otherAuthor = otherAuthor;
    }

    public ArticleBuffer(String title, String keywords, String articleAbstract,
                         String filePath, String firstAuthor, String otherAuthor,
                         Integer submitterId, Integer editorId, Integer overwrite) {
        this.title = title;
        this.keywords = keywords;
        this.articleAbstract = articleAbstract;
        this.filePath = filePath;
        this.firstAuthor = firstAuthor;
        this.otherAuthor = otherAuthor;
        this.submitterId = submitterId;
        this.editorId = editorId;
        this.overwrite = overwrite;
    }
}
