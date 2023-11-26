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
    @JoinColumn(name = "userId")
    private User user;
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

    public void setUser(User user) {
        this.user = user;
    }

    public Review(Integer id, User user, Product product, Integer rating, LocalDateTime dateReview, String comments) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.rating = rating;
        DateReview = dateReview;
        this.comments = comments;
    }


}

