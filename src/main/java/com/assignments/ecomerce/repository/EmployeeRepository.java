package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.assignments.ecomerce.model.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query("SELECT e from Employee e where status = 1")
    Page<Employee> pageEmployee(Pageable pageable);

    @Query("SELECT e from Employee e where e.status = 1 and CONCAT(e.fullname,e.address,e.phone,e.email,e.salary) like %?1%")
    List<Employee> searchEmployee(String keyword);
}
