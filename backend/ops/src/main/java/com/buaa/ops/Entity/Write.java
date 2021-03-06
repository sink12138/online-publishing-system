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
public class Write {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer writeId;

    private Integer authorId;
    private Integer articleId;
    private Boolean confirmed;

    public Write() {
    }

    public Write(Integer authorId, Integer articleId, Boolean confirmed) {
        this.authorId = authorId;
        this.articleId = articleId;
        this.confirmed = confirmed;
    }
}
