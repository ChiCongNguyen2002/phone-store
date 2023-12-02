package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String fullname;
    private String address;
    private String phone;
    private String email;
    private Integer statusCustomer;
    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatusCustomer(Integer statusCustomer) {
        this.statusCustomer = statusCustomer;
    }
}
