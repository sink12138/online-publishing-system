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
    private Integer author_id;

    private Integer account_id;
    private String institution;
    private String researchInterests;

    public Author() {

    }

    public Author(Integer account_id, String institution, String researchInterests) {
        this.account_id = account_id;
        this.institution = institution;
        this.researchInterests = researchInterests;
    }
}
