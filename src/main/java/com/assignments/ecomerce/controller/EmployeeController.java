package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Employee;
import com.assignments.ecomerce.service.CustomerService;
import com.assignments.ecomerce.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/search-employee/{pageNo}")
    public String searchCustomer(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal, HttpSession session) {

        Page<Employee> listEmployee = employeeService.searchEmployee(pageNo, keyword);
        session.setAttribute("keyword", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", listEmployee.getSize());
        model.addAttribute("listEmployee", listEmployee);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listEmployee.getTotalPages());
        model.addAttribute("employeeNew", new Employee());
        return "employee";
    }
}
