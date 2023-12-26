package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import com.assignments.ecomerce.service.OrderDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getAllOrderDetail() {
        return (List<OrderDetail>) orderDetailRepository.findAll();
    }

    public List<OrderDetail> findAllByOrderId(Integer orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }

    @Override
    public List<OrderDetail> findListProductByOrderId(Integer orderId) {
        return orderDetailRepository.findListProductByOrderId(orderId);
    }

    @Override
    @Transactional
    public void saveOrderDetail(Integer orderId, Integer productId, Integer quantity, Integer unitPrice) {
        orderDetailRepository.saveOrderDetail(orderId, productId, quantity, unitPrice);
    }
}
