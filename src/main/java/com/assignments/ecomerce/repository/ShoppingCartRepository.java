package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.CartItem;
import com.assignments.ecomerce.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {

}
