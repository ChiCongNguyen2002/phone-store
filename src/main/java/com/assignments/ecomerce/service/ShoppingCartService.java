package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {

    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

    public List<ShoppingCart> findAll();
}
