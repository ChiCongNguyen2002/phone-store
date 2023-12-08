package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class StatisticalController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @GetMapping("/statistical")
    public String countProducts(Model model, Principal principal) {
        int countProduct = productService.countProducts();
        int countUser = userService.countUsersByRole();
        int countOrder = orderService.countOrders();
        Double total = productService.getTotalRevenue();

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("total", total);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countUser", countUser);
        model.addAttribute("countProduct", countProduct);
        return "statistical";
    }

    @PostMapping("/ShowChartType")
    public String processForm(@RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                              @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                              @RequestParam(value = "year", defaultValue = "2023") int selectedYear,
                              @RequestParam("chartType") String chartType,
                              Model model, Principal principal) {
        List<Object> chartData = orderService.getData(dateFrom, dateTo, selectedYear, chartType);

        if (chartData != null) {
            if (chartData.isEmpty()) {
                throw new IllegalArgumentException("No data available for the selected chart type: " + chartType);
            }
            Object firstData = chartData.get(0);
            switch (firstData.getClass().getSimpleName()) {
                case "Top5Employee":
                    List<Top5Employee> top5Employees = chartData.stream()
                            .map(p -> (Top5Employee) p)
                            .collect(Collectors.toList());
                    System.out.println("controller" + top5Employees.size());
                    model.addAttribute("top5Employee", top5Employees);
                    break;
                case "TopCustomer":
                    List<TopCustomer> top5Users = chartData.stream()
                            .map(p -> (TopCustomer) p)
                            .collect(Collectors.toList());
                    model.addAttribute("top5Users", top5Users);
                    break;
                case "Product":
                    List<Product> topProducts = chartData.stream()
                            .map(p -> (Product) p)
                            .collect(Collectors.toList());
                    model.addAttribute("top10Products", topProducts);
                    break;
                case "MonthlyRevenue":
                    List<MonthlyRevenue> topMonth = chartData.stream()
                            .map(m -> (MonthlyRevenue) m)
                            .collect(Collectors.toList());
                    model.addAttribute("monthlyRevenue", topMonth);
                    break;

                case "WeeklyRevenue":
                    List<WeeklyRevenue> topWeek = chartData.stream()
                            .map(w -> (WeeklyRevenue) w)
                            .collect(Collectors.toList());
                    model.addAttribute("weeklyRevenue", topWeek);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid chart type: " + chartType);
            }
        } else {
            throw new IllegalArgumentException("No data available for the selected chart type: " + chartType);
        }
        int countProduct = productService.countProducts();
        int countOrder = orderService.countOrders();
        int countUser = userService.countUsersByRole();
        Double total = productService.getTotalRevenue();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("total", total);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countUser", countUser);
        model.addAttribute("countProduct", countProduct);
        return "statistical";
    }
}
