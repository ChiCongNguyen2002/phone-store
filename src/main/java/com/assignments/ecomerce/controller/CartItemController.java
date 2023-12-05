package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.ShoppingCart;
import com.assignments.ecomerce.service.CartItemService;
import com.assignments.ecomerce.service.CustomerService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.ShoppingCartService;
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
import com.assignments.ecomerce.model.Customer;

@Controller
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/cart")
    public String pageHome(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        return "cart";
    }

    @GetMapping("/Cart/checkout")
    public String pageCheckOut(Model model) {
        return "checkout";
    }

    @PostMapping("/add-to-cart")
    public String addToCart( Principal principal) {
/*        Product product = productService.getProductById(productId);*/

        /* Customer customer = customerService.findById(customerId);
        Product product = productService.findById(productId);
        ShoppingCart shoppingCart = shoppingCartService.getUserShoppingCart(product,product.getQuantity(),customer.getId());
        // Thực hiện thêm vào giỏ hàng
        cartItemService.addToCart(shoppingCart, product, quantity);*/
        return "redirect:/cart";
    }
}
