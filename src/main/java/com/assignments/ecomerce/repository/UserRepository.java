package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'USER'")
    int countUsersByRole();
}
