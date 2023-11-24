package com.assignments.ecomerce.service;

import com.assignments.ecomerce.dto.UserDTO;
import com.assignments.ecomerce.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByEmail(String email);
    User save (UserDTO userDto);
    User save(User user);

    int countUsersByRole();
}
