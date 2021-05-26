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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    private Integer accountId;
    private String institution;
    private String researchInterests;

    public Author() {

    }

    public Author(Integer accountId, String institution, String researchInterests) {
        this.accountId = accountId;
        this.institution = institution;
        this.researchInterests = researchInterests;
    }
}
