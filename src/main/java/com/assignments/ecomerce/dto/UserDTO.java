package com.assignments.ecomerce.dto;

import lombok.Getter;

@Getter
public class UserDTO {

    private String email;
    private String password;
    private String fullname;
    private String address;
    private String phone;
    private String role;

    public UserDTO(String email, String password, String role, String fullname, String address, String phone) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
    }

    public UserDTO(){}

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
}
