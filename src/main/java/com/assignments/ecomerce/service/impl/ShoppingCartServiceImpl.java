package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.repository.ShoppingCartRepository;
import com.assignments.ecomerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {

        return null;
    }

    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }
}
