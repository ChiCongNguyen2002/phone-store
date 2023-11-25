package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private UserService userService;

    @GetMapping("/statistical")
    public String countProducts(Model model) {
        int countProduct = productService.countProducts();
        int countUser = userService.countUsersByRole();
        int countOrder = orderService.countOrders();

        Double total = productService.getTotalRevenue();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);
        String formattedPrice = decimalFormat.format(total);

        model.addAttribute("total", formattedPrice);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countUser", countUser);
        model.addAttribute("countProduct", countProduct);
        return "statistical";
    }

    @PostMapping("/ShowChartType")
    public String processForm(@RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                               @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                              @RequestParam("chartType") String chartType,
                              Model model) {
        List<Object> chartData = orderService.getData(dateFrom, dateTo, chartType);

        if (chartData != null) {
            if (chartData.isEmpty()) {
                throw new IllegalArgumentException("No data available for the selected chart type: " + chartType);
            }
            Object firstData = chartData.get(0);

            switch (firstData.getClass().getSimpleName()) {
                case "User":
                    List<User> top5Users = chartData.stream()
                            .map(p -> (User) p)
                            .collect(Collectors.toList());
                    model.addAttribute("top5Users", top5Users);
                    break;
                case "Product":
                    List<Product> topProducts = chartData.stream()
                            .map(p -> (Product) p)
                            .collect(Collectors.toList());

                    List<String> formattedPrices = new ArrayList<>();
                    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
                    decimalFormatSymbols.setGroupingSeparator('.');
                    DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

                    for (Product product : topProducts) {
                        String formattedPrice = decimalFormat.format(product.getPrice());
                        formattedPrices.add(formattedPrice);
                    }

                    model.addAttribute("top10Products", topProducts);
                    model.addAttribute("formattedPrices", formattedPrices);
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
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);
        String formattedPrice = decimalFormat.format(total);

        model.addAttribute("total", formattedPrice);
        model.addAttribute("countOrder", countOrder);
        model.addAttribute("countUser", countUser);
        model.addAttribute("countProduct", countProduct);
        return "statistical";
    }
}
