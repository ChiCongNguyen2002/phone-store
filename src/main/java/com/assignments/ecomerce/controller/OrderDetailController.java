package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.service.OrderDetailService;
import com.assignments.ecomerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;
    @GetMapping("/orderdetail")
    public String getAllOrderDetail(@RequestParam("orderId") Integer orderId, Model model) {
        List<Orders> listOrder = new ArrayList<>();
        Orders order = orderService.getOrderById(orderId);
        if (order != null) {
            listOrder.add(order);
        }
        model.addAttribute("listOrder", listOrder);
        List<OrderDetail> listOrderDetail = orderDetailService.findAllByOrderId(orderId);

        List<String> formattedPrices = new ArrayList<>();
        List<String> formattedPriceDetail = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }

        for (OrderDetail orderDetail : listOrderDetail) {
            String formattedPrice = decimalFormat.format(orderDetail.getUnitPrice());
            formattedPriceDetail.add(formattedPrice);
        }
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("formattedPriceDetail", formattedPriceDetail);
        model.addAttribute("listOrderDetail", listOrderDetail);
        return "orderdetail";
    }
}
