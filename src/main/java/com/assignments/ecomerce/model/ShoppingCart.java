package com.assignments.ecomerce.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

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
    private Customer customer;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "shoppingCart")
    private List<CartItem> cartItems;
    private double totalPrice;
    private int totalItems;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
