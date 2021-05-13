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
    private int writeId;

    private int authorId;
    private int articleId;

    public Write() {
    }

    public Write(int authorId, int articleId) {
        this.authorId = authorId;
        this.articleId = articleId;
    }
}
