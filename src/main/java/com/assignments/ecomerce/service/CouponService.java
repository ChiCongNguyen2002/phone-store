package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CouponService {

    List<Coupon> getAllCoupons();

    Coupon save(Coupon coupon);

    Page<Coupon> pageCoupon(int pageNo);

    Coupon findById(Integer id);

    Coupon update(Coupon coupon);

    void deleteById(Integer id);

    void enableById(Integer id);

    Page<Coupon> searchCoupon(int pageNo, String keyword);

    Page toPage(List<Coupon> list, Pageable pageable);

    List<Coupon> transfer(List<Coupon> coupons);
}
