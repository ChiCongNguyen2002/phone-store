package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface EmployeeService {

    List<Employee> getALlEmployees();

    Page<Employee> pageEmployee(int pageNo);

     Employee save(Employee employee);

     Employee findById(Integer id);

     Employee update(Employee employee);

     void deleteById(Integer id) ;

     void setStatus(Integer id) ;

     Page<Employee> searchEmployees(int pageNo, String keyword);
     public Page toPage(List<Employee> list, Pageable pageable) ;

     List<Employee> transfer(List<Employee> employees);

     Employee updateStatus(Integer id);
}
