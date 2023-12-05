package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.CartItem;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {
    ShoppingCart findByCustomer(Customer customer);
}
