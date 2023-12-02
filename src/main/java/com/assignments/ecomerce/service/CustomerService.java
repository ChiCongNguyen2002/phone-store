package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Page<Customer> pageCustomer(int pageNo);

    Page<Customer> searchCustomer(int pageNo, String keyword);
    void blockCustomer(Integer id);
    void unlockCustomer(Integer id);
}
