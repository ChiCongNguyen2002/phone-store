package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "forgot_password_token")
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime expireTime;

    @Column(nullable = false)
    private Boolean isuUsed;

    public ForgotPasswordToken() {
    }

    public ForgotPasswordToken(Integer id, String token, User user, LocalDateTime expireTime, Boolean isuUsed) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expireTime = expireTime;
        this.isuUsed = isuUsed;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public void setIsuUsed(Boolean isuUsed) {
        this.isuUsed = isuUsed;
    }

}


