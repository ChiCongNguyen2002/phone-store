package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {

     Orders findById(Integer id);

     List<Orders> getAllOrders() ;

     Orders getOrderById(Integer id);

     int countOrders();

     Page<Orders> pageOrders(int pageNo);

     Page<Orders> searchOrders(int pageNo, String keyword);
     Page toPage(List<Orders> list, Pageable pageable);
     List<Orders> transfer(List<Orders> orders);

     List<Object> getData(Date dateFrom, Date dateTo, String chartType);
}
