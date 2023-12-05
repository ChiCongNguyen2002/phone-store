package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.CartItemRepository;
import com.assignments.ecomerce.repository.ShoppingCartRepository;
import com.assignments.ecomerce.service.CustomerService;
import com.assignments.ecomerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CustomerService customerService;

    @Override
    public ShoppingCart getUserShoppingCart(Product product, int quantity, int accountId) {
        // Lấy thông tin khách hàng (người dùng) dựa trên accountId
        Customer customer = customerService.findById(accountId);

        if (customer != null) {
            // Kiểm tra xem khách hàng đã có giỏ hàng hay chưa
            ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer(customer);
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart();
                shoppingCart.setCustomer(customer);
                shoppingCart.setCartItems(new ArrayList<>());
                shoppingCart = shoppingCartRepository.save(shoppingCart);
            }
            return shoppingCart;
        }

        return null;
    }

    @Override
    public List<ShoppingCart> findAll() {
        return null;
    }
}
