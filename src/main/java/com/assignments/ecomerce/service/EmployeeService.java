package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EmployeeService {
    Page<Employee> pageEmployee(int pageNo);

    Page<Employee> searchEmployee(int pageNo,String keyword);

    Employee findById(Integer id);

    Employee update(Employee employee);

    void deleteById(Integer id);

    Employee findByPhoneAndEmail(String phone, String email);

    Employee save(Employee employee);

    Employee updateStatus(Integer id);

    Employee findIdByUser(Integer userId);
}
