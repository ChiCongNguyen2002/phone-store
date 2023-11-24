package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.CartItem;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.repository.CartItemRepository;
import com.assignments.ecomerce.repository.ShoppingCartRepository;
import com.assignments.ecomerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Override
    public ShoppingCart addItemToCart(Product product, int quantity, User user) {
        return null;
    }

    @Override
    public List<ShoppingCart> findAll() {
        return null;
    }
}
