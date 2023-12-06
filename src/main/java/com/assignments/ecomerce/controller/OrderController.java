package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/order/{pageNo}")
    public String getAllOrder(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/search-order/{pageNo}")
    public String searchOrder(@PathVariable("pageNo") int pageNo,
                              @RequestParam("keyword") String keyword,
                              Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrders(pageNo, keyword);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/search-order-ByEmployee/{pageNo}")
    public String searchOrderByEmployee(@PathVariable("pageNo") int pageNo,
                                        @RequestParam("keyword") String keyword,
                                        Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrders(pageNo, keyword);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "OrderManager";
    }


    @GetMapping("/UpdateOrderStatus/{pageNo}/{id}")
    public String UpdateOrderStatus(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("id") Integer id, Model model) {
        Orders order = orderService.getById(id);
        int status = order.getStatus();
        switch (status) {
            case 1:
                order.setStatus(2);
                orderService.save(order);
                break;
            case 2:
                order.setStatus(3);
                orderService.save(order);
                break;
            case 3:
                order.setStatus(4);
                orderService.save(order);
                break;
        }
        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "redirect:/search-order/0?keyword=";
    }

    @GetMapping("/CancelOrderStatus/{pageNo}/{id}")
    public String CancelOrderStatus(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("id") Integer id, Model model) {
        Orders order = orderService.getById(id);
        order.setStatus(5);
        orderService.save(order);

        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "redirect:/search-order/0?keyword=";
    }

    @GetMapping("/CancelOrderStatusByUser/{pageNo}/{id}")
    public String CancelOrderStatusByUser(@PathVariable("pageNo") int pageNo,
                                          @PathVariable("id") Integer id, Model model, Principal principal) {
        Orders order = orderService.getById(id);
        order.setStatus(5);
        orderService.save(order);
        User user = userService.findByEmailUser(principal.getName());
        Page<Orders> listOrder = orderService.pageOrdersById(pageNo, user.getId());
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        model.addAttribute("userDetail", user);
        return "accountUser";
    }

    @GetMapping("/search-order-by-time/{pageNo}")
    public String searchOrderByTime(@PathVariable("pageNo") int pageNo,
                                    @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                    @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                    Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrdersByTime(pageNo, dateFrom, dateTo);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/search-order-by-timeByEmployee/{pageNo}")
    public String searchOrderByTimeByEmployee(@PathVariable("pageNo") int pageNo,
                                              @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                              @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                              Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrdersByTime(pageNo, dateFrom, dateTo);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "redirect:/search-order-ByEmployee/0?keyword=";
    }

    @GetMapping("/UpdateOrderStatusByEmployee/{pageNo}/{id}")
    public String UpdateOrderStatusByEmployee(@PathVariable("pageNo") int pageNo,
                                              @PathVariable("id") Integer id, Model model) {
        Orders order = orderService.getById(id);
        int status = order.getStatus();
        switch (status) {
            case 1:
                order.setStatus(2);
                orderService.save(order);
                break;
            case 2:
                order.setStatus(3);
                orderService.save(order);
                break;
            case 3:
                order.setStatus(4);
                orderService.save(order);
                break;
        }
        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "redirect:/search-order-ByEmployee/0?keyword=";
    }

    @GetMapping("/cart")
    public String pageCart( Model model, Principal principal) {
        UserDetails userDetails = null;
        if (principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }
        User user = userService.findByEmail(principal.getName());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("userId", user.getId());
        model.addAttribute("userDetails", userDetails);
        return "cart";
    }
}
