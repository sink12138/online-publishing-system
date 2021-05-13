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
public class AccountBuffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountBufferId;

    private String email;
    private String password;

    public AccountBuffer() {

    }

    public AccountBuffer(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
