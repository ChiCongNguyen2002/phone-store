package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.repository.CategoryRepository;
import com.assignments.ecomerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

   /* public ShoppingCart addItemToCart(Product product, int quantity, Customer customer){

    }*/
}
