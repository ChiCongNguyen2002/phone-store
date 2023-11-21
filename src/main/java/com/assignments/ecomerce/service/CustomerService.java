package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.CustomerDTO;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface CustomerService {

    List<Customer> getAllCustomers();

    Page<Customer> pageCustomer(int pageNo);

    int countCustomers();

    Customer save(CustomerDTO customer) ;

    Customer findById(Integer id);

    Customer update(CustomerDTO customer);

    void deleteById(Integer id);

    void enableById(Integer id);

    Page<Customer> searchCustomer(int pageNo, String keyword);

    public Page toPage(List<Customer> list, Pageable pageable);

    List<Customer> transfer(List<Customer> customers);

    Customer updateStatus(Integer id) ;

    boolean checkIfEmailExists(String email);

    boolean checkIfPhoneExists(String phone);
}
