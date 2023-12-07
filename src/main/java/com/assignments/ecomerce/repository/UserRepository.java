package com.assignments.ecomerce.repository;

import com.assignments.ecomerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByPhone(String phone);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'USER'")
    int countUsersByRole();

    @Query("SELECT u FROM User u WHERE u.email = :email and u.role = 'USER'")
    User findByEmailUser(String email);

    @Query("SELECT c from User c where CONCAT(c.fullname,c.address,c.phone,c.email) like %?1%")
    List<User> searchUser(String keyword);
}
