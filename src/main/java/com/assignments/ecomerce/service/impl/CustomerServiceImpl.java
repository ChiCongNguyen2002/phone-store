package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.CustomerRepository;
import com.assignments.ecomerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Page<Customer> pageCustomer(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 3);
        return customerRepository.pageCustomer(pageable);
    }

    @Override
    public Page<User> findByKeyword(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<User> customners = transfer(customerRepository.findByKeyword(keyword.trim()));
        Page<User> customnersPages = toPage(customners, pageable);
        return customnersPages;
    }

    @Override
    public void blockCustomer(Integer id) {
        Customer customer = customerRepository.getById(id);
        customer.setStatusCustomer(0);
        customerRepository.save(customer);
    }

    @Override
    public void unlockCustomer(Integer id) {
        Customer customer = customerRepository.getById(id);
        customer.setStatusCustomer(1);
        customerRepository.save(customer);
    }

    @Override
    public Customer findByPhoneAndEmail(String phone, String email) {
        return customerRepository.findByPhoneAndEmail(phone,email);
    }

    @Override
    public Customer save(Customer customer) {
        customer.setStatusCustomer(1);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateStatus(Integer id) {
        Customer customer = customerRepository.getById(id);
        customer.setStatusCustomer(1);
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.getById(id);
    }

    private Page toPage(List<User> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<User> transfer(List<User> users) {
        List<User> customerList = new ArrayList<>();
        for (User customer : users) {
            User newCustomer = new User();
            newCustomer.setId(customer.getId());
            newCustomer.setFullname(customer.getFullname());
            newCustomer.setAddress(customer.getAddress());
            newCustomer.setPhone(customer.getPhone());
            newCustomer.setEmail(customer.getEmail());
            customerList.add(newCustomer);
        }
        return customerList;
    }
}
