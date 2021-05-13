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
    private int author_id;

    private int account_id;
    private String institution;
    private String researchInterests;

    public Author() {

    }

    public Author(int account_id, String institution, String researchInterests) {
        this.account_id = account_id;
        this.institution = institution;
        this.researchInterests = researchInterests;
    }
}
