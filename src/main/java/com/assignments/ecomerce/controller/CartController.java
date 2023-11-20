package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.service.CustomerService;
import com.assignments.ecomerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/Cart/GetListItems")
    public String pageHome(Model model, Principal principal, HttpSession session) {
        return "cart";
    }

    @GetMapping("/Cart/checkout")
    public String pageCheckOut(Model model) {
        return "checkout";
    }

    @PostMapping("/Cart/add-to-cart")
    public String pageAddToCart(Model model, HttpSession session, Principal principal) {
      /*  if (principal == null) {
            return "redirect:/login";
        }*/
         //Customer customer = customerService.findByUsername(principal.getName());
        //ShoppingCart cart = customer.getCart();
        //session.setAttribute("totalItems", cart.getTotalItems());
        return "cart";
    }
}
