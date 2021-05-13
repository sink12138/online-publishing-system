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
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewerId;

    private Integer accountId;
    private String organization;

    public Reviewer() {
    }

    public Reviewer(Integer accountId, String organization) {
        this.accountId = accountId;
        this.organization = organization;
    }
}
