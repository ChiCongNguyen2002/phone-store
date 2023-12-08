package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.CouponService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class CompareController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CouponService couponService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/compare/{productId}")
    public String compareProducts(@PathVariable("productId") Integer productId, Model model, Principal principal) {
        if(principal == null){
            Product product = productService.getProductById(productId);
            List<Product> listProducts = productService.getAllProducts();
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("product", product);
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("categories", categories);
            return "compareProductUser";
        }else{
            Product product = productService.getProductById(productId);
            User user = userService.findByEmail(principal.getName());
            UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
            List<Product> listProducts = productService.getAllProducts();
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("name", userDetails);
            model.addAttribute("userId", user.getId());
            model.addAttribute("product", product);
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("categories", categories);
            model.addAttribute("userDetails", userDetails);
            return "compareProductUser";
        }
    }

    @GetMapping("/getProductOptions")
    @ResponseBody
    public List<Product> getProductOptions(){
        List<Product> productList = productService.getAllProducts();
        return productList;
    }

    @GetMapping("/getProductInfo")
    @ResponseBody
    public Product getProductInfo(@RequestParam("productId") Integer productId){
//        Product product = productService.findById(productId);
        Product product = productService.findById(productId);
        return product;
    }
}
