package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Review;
import com.assignments.ecomerce.repository.ReviewRepository;
import com.assignments.ecomerce.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReview() {
        return reviewRepository.findAll();
    }

    public List<Review> getByProduct(Product product) {
        return reviewRepository.getByProduct(product);
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public int countReviews(Product product) {
        return reviewRepository.countReviews(product);
    }

    public Double calculateAverageRating(Product product) {
        return reviewRepository.calculateAverageRating(product);
    }
    public boolean existsByCustomerIdAndProductId(Integer customerId, Integer productId) {
        return reviewRepository.existsByCustomerIdAndProductId(customerId,productId);
    }

}
