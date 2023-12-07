package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Review;
import com.assignments.ecomerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

     List<Review> getAllReview();

     List<Review> getByProduct(Product product);

     Review save(Review review);

     int countReviews(Product product);

     Double calculateAverageRating(Product product);
     boolean existsByCustomerIdAndProductId(Integer customerId, Integer productId);

}
