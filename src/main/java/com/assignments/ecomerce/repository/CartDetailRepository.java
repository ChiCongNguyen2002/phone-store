package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.CartDetail;
import com.assignments.ecomerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    CartDetail findByUserIdAndProductId(Integer userId, Integer productId);
    List<CartDetail> findByUserId(Integer userId);
    @Modifying
    @Query(value = "INSERT INTO CartDetail (userId, productId, quantity, unitPrice) VALUES (:userId, :productId, :quantity, :unitPrice)", nativeQuery = true)
    int saveCartDetail(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("quantity") Integer quantity, @Param("unitPrice") Integer unitPrice);
}
