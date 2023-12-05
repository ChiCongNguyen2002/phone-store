package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    void addToCart(ShoppingCart shoppingCart, Product product, int quantity);
}
