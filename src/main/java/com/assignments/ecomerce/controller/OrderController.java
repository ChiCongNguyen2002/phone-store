package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.model.Product;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }
        model.addAttribute("formattedPrices", formattedPrices);
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

    @GetMapping("/search-order-by-time/{pageNo}")
    public String searchOrderByTime(@PathVariable("pageNo") int pageNo,
                                    @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                    @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                    Model model, Principal principal) {
        Page<Orders> listOrder = orderService.searchOrdersByTime(pageNo, dateFrom,dateTo);
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/UpdateOrderStatus/{pageNo}/{id}")
    public String UpdateOrderStatus(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("id") Integer id, Model model) {
        Orders order = orderService.getById(id);
        int status = order.getStatus();
        switch (status)
        {
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
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/CancelOrderStatus/{pageNo}/{id}")
    public String CancelOrderStatus(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("id") Integer id, Model model) {
        Orders order = orderService.getById(id);
        order.setStatus(5);
        orderService.save(order);

        Page<Orders> listOrder = orderService.pageOrders(pageNo);
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "order";
    }

    @GetMapping("/CancelOrderStatusByUser/{pageNo}/{id}")
    public String CancelOrderStatusByUser(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("id") Integer id, Model model, Principal principal) {
        Orders order = orderService.getById(id);
        order.setStatus(5);
        orderService.save(order);
        User user = userService.findByEmailUser(principal.getName());
        Page<Orders> listOrder = orderService.pageOrdersById(pageNo, user.getId());
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        model.addAttribute("userDetail", user);
        return "accountUser";
    }
}
