package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Entity
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer rating;
    private LocalDateTime DateReview;
    private String comments;

    public void setDateReview(LocalDateTime dateReview) {
        DateReview = dateReview;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Review(){}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Review(Integer id, Customer customer, Product product, Integer rating, LocalDateTime dateReview, String comments) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.rating = rating;
        DateReview = dateReview;
        this.comments = comments;
    }
}

