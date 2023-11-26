package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDTO;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") UserDTO userDto, Model model){
        return "register";
    }

    @PostMapping("/registration")
    public  String saveUser(@ModelAttribute("user") User userDto, Model model){
        userService.save(userDto);
        model.addAttribute("message","Registered successfully");
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/indexCustomer")
    public String userPage (Model model, Principal principal) {

        List<Product> listProducts = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategory();
        List<Product> topProducts = productService.getData();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("top10Products", topProducts);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);

        return "indexUser";
    }

    @GetMapping("/index")
    public String adminPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "statistical";
    }
}
