package com.buaa.ops.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Setter
@Getter
public class Check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkId;

    private String code;
    private String email;
    private Date checkingTime;

    public Check() {
    }

    public Check(String code, String email, Date checkingTime) {
        this.code = code;
        this.email = email;
        this.checkingTime = checkingTime;
    }
}
