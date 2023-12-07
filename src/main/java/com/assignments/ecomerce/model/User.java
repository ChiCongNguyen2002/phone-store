package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String role;
    private String fullname;
    private String address;
    private String phone;
    private Integer status;

    public User(String email, String password, String role, String fullname) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public User(String fullname) {
        this.fullname = fullname;
    }

    public User() {
    }

    public User(String email, String password, String role, String fullname, String address, String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    public User(Integer id, String email, String password, String role, String fullname, String address, String phone, Integer status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

