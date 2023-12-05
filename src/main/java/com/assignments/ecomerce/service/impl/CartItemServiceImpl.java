package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.CartItem;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.repository.CartItemRepository;
import com.assignments.ecomerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    public void addToCart(ShoppingCart shoppingCart, Product product, int quantity) {

    }
}
