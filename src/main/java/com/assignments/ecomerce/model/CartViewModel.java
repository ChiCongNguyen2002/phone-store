package com.assignments.ecomerce.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import java.util.List;

@Getter
public class CartViewModel {
    public List<CartDetail> CartItems;
    public Integer Promotion;
    public String CouponCode;

    public void setCartItems(List<CartDetail> cartItems) {
        CartItems = cartItems;
    }

    public void setPromotion(Integer promotion) {
        Promotion = promotion;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }
}
