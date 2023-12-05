package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.repository.EmployeeRepository;
import com.assignments.ecomerce.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> pageEmployee(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 3);
        return employeeRepository.pageEmployee(pageable);
    }

    @Override
    public Page<Employee> searchEmployee(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<Employee> employees = transfer(employeeRepository.searchEmployee(keyword.trim()));
        Page<Employee> employeePages = toPage(employees, pageable);
        return employeePages;
    }

    @Override
    public Employee findById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee update(Employee employee) {
        Employee employeeSave = null;
        try {
            employeeSave = employeeRepository.findById(employee.getId()).get();
            employeeSave.setFullname(employee.getFullname());
            employeeSave.setPhone(employee.getPhone());
            employeeSave.setAddress(employee.getAddress());
            employeeSave.setEmail(employee.getEmail());
            employeeSave.setSalary(employee.getSalary());
            employeeSave.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeRepository.save(employeeSave);
    }

    @Override
    public void deleteById(Integer id) {
        Employee employee = employeeRepository.getById(id);
        employee.setStatus(0);
        employeeRepository.save(employee);
    }

    @Override
    public Employee findByPhoneAndEmail(String phone, String email) {
        return employeeRepository.findByPhoneAndEmail(phone, email);
    }

    @Override
    public Employee save(Employee employee) {
        employee.setStatus(1);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateStatus(Integer id) {
        Employee employee = employeeRepository.getById(id);
        employee.setStatus(1);
        return employeeRepository.save(employee);
    }

    private Page toPage(List<Employee> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    private List<Employee> transfer(List<Employee> employees) {
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            Employee newEmployee = new Employee();
            newEmployee.setId(employee.getId());
            newEmployee.setFullname(employee.getFullname());
            newEmployee.setAddress(employee.getAddress());
            newEmployee.setPhone(employee.getPhone());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setSalary(employee.getSalary());
            newEmployee.setStatus(employee.getStatus());
            employeeList.add(newEmployee);
        }
        return employeeList;
    }
}
