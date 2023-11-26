package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/cart")
    public String pageHome(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        /*List<ShoppingCart> listShoppingCart = shoppingCartService.findAll();
        model.addAttribute("size", listShoppingCart.size());*/
        return "cart";
    }

    @GetMapping("/Cart/checkout")
    public String pageCheckOut(Model model) {
        return "checkout";
    }

    @PostMapping("/Cart/add-to-cart")
    public String pageAddToCart(@RequestParam("id") Integer id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {
        return "cart";
    }
}
