package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {
    Page<Employee> pageEmployee(int pageNo);

    Page<Employee> searchEmployee(int pageNo,String keyword);
}
