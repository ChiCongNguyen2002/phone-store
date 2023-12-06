package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartDetailService {
    List<CartDetail> findAll();

    List<CartDetail> findByUserId(Integer userId);

    CartDetail findByUserIdAndProductId(Integer userId, Integer productId);

    boolean saveCart(Integer userId, Integer productId, Integer quantity, Integer unitPrice);

    boolean deleteCart(Integer userId, Integer productId);
}
