package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query("SELECT e from Employee e where status = 1")
    Page<Employee> pageEmployee(Pageable pageable);

    @Query("SELECT e from Employee e where e.status = 1 and CONCAT(e.fullname,e.address,e.phone,e.email,e.salary) like %?1%")
    List<Employee> searchEmployee(String keyword);

    @Query(value = "SELECT e from Employee e where e.status = 1 and e.phone = ?1 OR e.email = ?2")
    Employee findByPhoneAndEmail(String phone, String email);

    @Query(value = "SELECT * FROM Employee e WHERE e.userId = :userId", nativeQuery = true)
    Employee getIdByUser(Integer userId);
}
