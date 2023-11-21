package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/fragments")
    public String page() {
        return "fragments";
    }

    @GetMapping("/login")
    public String pageLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String pageIndex(Principal principal) {
        return "index";
    }

    @GetMapping("/forgotPassword")
    public String pageForgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }
}
