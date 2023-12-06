package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.OrderDetailService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/account/{pageNo}")
    public String showAccountUser(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userService.findByEmailUser(principal.getName());
        Page<Orders> listOrder = orderService.pageOrdersById(pageNo, user.getId());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("userId", user.getId());
        model.addAttribute("categories", categories);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userDetail", user);
        return "accountUser";
    }

    @GetMapping("/EditAccount/{pageNo}/{userId}")
    public String editAccountUser(@PathVariable("pageNo") int pageNo,
                                  @PathVariable("userId") Integer userId, Model model, Principal principal) {
        User user = userService.getById(userId);
        List<Category> categories = categoryService.getAllCategory();

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user1 = userService.findByEmailUser(principal.getName());
        model.addAttribute("userId", user1.getId());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("categories", categories);
        model.addAttribute("userDetail", user);
        return "editAccountUser";
    }

    @PostMapping("/OrderDetailByUser/{pageNo}")
    public String OrderDetailByUser(@PathVariable("pageNo") int pageNo,
                                    @RequestParam("orderId") Integer orderId, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        List<Orders> listOrder = new ArrayList<>();
        Orders order = orderService.getOrderById(orderId);
        if (order != null) {
            listOrder.add(order);
        }

        List<OrderDetail> listOrderDetail = orderDetailService.findAllByOrderId(orderId);
        User user = userService.findByEmailUser(principal.getName());
        model.addAttribute("userId", user.getId());
        model.addAttribute("order", order);
        model.addAttribute("listOrderDetail", listOrderDetail);
        model.addAttribute("userDetails", userDetails);
        return "orderDetailUser";
    }

    @PostMapping("/EditAccount/{pageNo}/{userId}")
    public String editAccountUser(@PathVariable("pageNo") int pageNo,
                                  @PathVariable("userId") Integer userId, @ModelAttribute("userDetail")
                                  User user, RedirectAttributes attributes) {
        try {
            userService.update(user);
            attributes.addFlashAttribute("success", "Update successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update");
        }
        return "redirect:/EditAccount/" + pageNo + "/" + userId;
    }

    @PostMapping("/add-account")
    public String AddAccount(@ModelAttribute("accountNew") User userDto, Model model, RedirectAttributes attributes) {
        User user = userService.findByEmail(userDto.getEmail());
        if (user != null) {
            model.addAttribute("userexist", user);
            attributes.addFlashAttribute("error", "Email đã tồn tại Vui lòng nhập email khác");
            return "redirect:/search-user/0?keyword=";
        }
        try {
            userService.save(userDto);
            model.addAttribute("accountNew", userDto);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("error", "Duplicate name of account, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("error", "Error Server");
        }
        return "redirect:/search-user/0?keyword=";
    }
}
