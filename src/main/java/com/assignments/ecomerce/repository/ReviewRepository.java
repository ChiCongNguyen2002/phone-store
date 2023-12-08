package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    @Query("SELECT r FROM Review r WHERE r.product = :product")
    List<Review> getByProduct(Product product);

    @Query(value = "SELECT COUNT(*) FROM Review r WHERE r.product = :product")
    int countReviews(Product product);

    @Query(value = "SELECT ROUND(AVG(r.rating), 1) FROM Review r WHERE r.product = :product")
    Double calculateAverageRating(Product product);

    @Query(value = "SELECT * FROM review WHERE customerId = :customerId AND productId = :productId LIMIT 1", nativeQuery = true)
    Review existsByCustomerIdAndProductId(@Param("customerId") Integer customerId, @Param("productId") Integer productId);
}
