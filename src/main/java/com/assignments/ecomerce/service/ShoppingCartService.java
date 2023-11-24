package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {

    public ShoppingCart addItemToCart(Product product, int quantity, User user);

    public List<ShoppingCart> findAll();
}
