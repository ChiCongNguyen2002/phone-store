package com.assignments.ecomerce.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@Entity
@Table(name = "ShoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "shoppingCart")
    private List<CartItem> cartItems;
    private double totalPrice;
    private int totalItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }
}
