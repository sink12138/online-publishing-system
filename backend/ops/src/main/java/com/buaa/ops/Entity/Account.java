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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    private String email;
    private String password;
    private String realName;

    public Account() {

    }

    public Account(String email, String password, String realName) {
        this.email = email;
        this.password = password;
        this.realName = realName;
    }
}
