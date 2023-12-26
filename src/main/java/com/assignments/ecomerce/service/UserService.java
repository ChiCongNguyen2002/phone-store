package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.UserDTO;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void blockAccount(Integer id);
    void unlockAccount(Integer id);
    User findByPhone(String phone);
    User findByEmail(String email);
    User saveAdmin(User user);
    User save(User user);
    int countUsersByRole();
    User findByEmailUser(String email);
    User getById(Integer id);
    User update(User user);
    Page<User> searchUser(int pageNo, String keyword);

    User findByIdAdmin(Integer id);
}
