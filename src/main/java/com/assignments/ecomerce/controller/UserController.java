package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDTO;
import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Customer;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/registration")
    public  String saveUser(@ModelAttribute("user") User userDto, Model model){
        User user = userService.findByEmail(userDto.getEmail());
        if (user != null) {
            model.addAttribute("userexist", user);
            return "register";

        }
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
        UserDetails userDetails = null;
        if (principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("top10Products", topProducts);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        return "indexUser";
    }

    @GetMapping({"/", "/index"})
    public String adminPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "statistical";
    }

    @GetMapping("/search-user/{pageNo}")
    public String searchUser(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal, HttpSession session) {

        Page<User> listUser = userService.searchUser(pageNo, keyword);
        session.setAttribute("keyword", keyword);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", listUser.getSize());
        model.addAttribute("listUser", listUser);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listUser.getTotalPages());
        model.addAttribute("customerNew", new Customer());
        return "accountAdmin";
    }

    @GetMapping("/permission")
    public String permission() {
        return "permission";
    }
}
