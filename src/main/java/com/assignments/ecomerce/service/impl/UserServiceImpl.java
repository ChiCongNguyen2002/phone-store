package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.dto.UserDTO;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.repository.UserRepository;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(UserDTO userDto) {
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getFullname());
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public int countUsersByRole() {
        return userRepository.countUsersByRole();
    }

    @Override
    public User findByEmailUser(String email) {
        return userRepository.findByEmailUser(email);
    }

    @Override
    public User getById(Integer id) {
        User user = userRepository.getById(id);
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setAddress(user.getAddress());
        newUser.setPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setFullname(user.getFullname());
        return newUser;
    }

    @Override
    public User update(User user) {
        try {
            User userUpdate = userRepository.getById(user.getId());
            userUpdate.setId(user.getId());
            userUpdate.setPhone(user.getPhone());
            userUpdate.setAddress(user.getAddress());
            userUpdate.setFullname(user.getFullname());
            userUpdate.setEmail(user.getEmail());
            userRepository.save(userUpdate);
            return userUpdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
