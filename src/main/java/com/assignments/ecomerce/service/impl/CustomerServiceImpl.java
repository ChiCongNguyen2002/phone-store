package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.Supplier;
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
    public Page<Customer> searchCustomer(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Customer> categorys = transfer(customerRepository.searchCustomer(keyword.trim()));
        Page<Customer> categoryPages = toPage(categorys, pageable);
        return categoryPages;
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

    private Page toPage(List<Customer> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<Customer> transfer(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        for (Customer customer : customers) {
            Customer newCustomer = new Customer();
            newCustomer.setId(customer.getId());
            newCustomer.setFullname(customer.getFullname());
            newCustomer.setAddress(customer.getAddress());
            newCustomer.setPhone(customer.getPhone());
            newCustomer.setEmail(customer.getEmail());
            newCustomer.setStatusCustomer(customer.getStatusCustomer());
            customerList.add(newCustomer);
        }
        return customerList;
    }
}
