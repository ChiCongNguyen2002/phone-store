package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.OrderDetailRepository;
import com.assignments.ecomerce.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ForgotPasswordService emailService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

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
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "OrderManager";
    }


    @GetMapping("/UpdateOrderStatus/{pageNo}/{id}")
    public String UpdateOrderStatus(@PathVariable("pageNo") int pageNo,
                                    @PathVariable("id") Integer id, Model model) throws MessagingException, UnsupportedEncodingException {
        Orders order = orderService.getById(id);
        int status = order.getStatus();
        switch (status) {
            case 1:
                order.setStatus(2);
                order.setEmployee(order.getEmployee());
                order.setShipName(order.getShipName());
                order.setShipPhoneNumber(order.getShipPhoneNumber());
                //update order status
                orderService.save(order);

                //set quantity product
                List<OrderDetail> orderDetails = orderDetailService.findListProductByOrderId(order.getId());
                for (OrderDetail od : orderDetails) {
                    Product product = productService.getProductById(od.getProduct().getId());
                    int quan = product.getQuantity();
                    int newQuan = quan - od.getQuantity();
                    if (newQuan < 0) {
                        //update order status
                        order.setStatus(1);
                        orderService.save(order);
                        String message = "Sản phẩm với ID: " + product.getId() + " không đủ số lượng. \n\n Vui lòng kiểm tra số lượng sản phẩm trước khi xác nhận ";
                        return message;
                    }
                    product.setQuantity(newQuan);
                    productService.updateQuantity(product);
                }
                //send email confirm
                emailService.sendEmailConfirmOrder(order.getCustomer().getEmail(),
                        "Order Confirmation",
                        order.getEmployee().getFullname(),
                        orderDetails);
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
                                    @PathVariable("id") Integer id, Model model) throws MessagingException, UnsupportedEncodingException {
        Orders order = orderService.getById(id);
        order.setStatus(5);
        orderService.save(order);

        emailService.sendEmailCancelOrder(order.getCustomer().getEmail(), "Order Cancel", order.getEmployee().getFullname());

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
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Page<Orders> listOrder = orderService.searchOrdersByTimeEmployee(pageNo, dateFrom, dateTo);
        for (Orders order : listOrder.getContent()) {
            System.out.println(order);
        }
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "OrderManager";
    }

    @GetMapping("/UpdateOrderStatusByEmployee/{pageNo}/{id}")
    public String UpdateOrderStatusByEmployee(@PathVariable("pageNo") int pageNo,
                                              @PathVariable("id") Integer id, Model model, Principal principal) {
        Orders order = orderService.getById(id);
        Orders orderByInforEmployee = orderService.getEmployeeById(id);
        int status = order.getStatus();
        switch (status) {
            case 1:
                order.setStatus(2);
                order.setEmployee(orderByInforEmployee.getEmployee());
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "redirect:/search-order-ByEmployee/0?keyword=";
    }

    @GetMapping("/cart")
    public String pageCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDetails userDetails = null;
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            User user = userService.findByEmail(principal.getName());
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("categories", categories);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("name", userDetails);
            model.addAttribute("userDetails", userDetails);
            return "cart";
        }
    }

    @GetMapping("/Cart/checkout/{coupon}")
    public String pageCheckOut(@PathVariable("coupon")String Coupon, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDetails userDetails = null;
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            User user = userService.findByEmail(principal.getName());
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("coupon", Coupon);
            model.addAttribute("categories", categories);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("name", userDetails);
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("user", user);
            return "checkoutUser";
        }
    }

    @GetMapping("/checkoutSuccess")
    public String pageCheckOut(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDetails userDetails = null;
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            User user = userService.findByEmail(principal.getName());
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("categories", categories);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("name", userDetails);
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("user", user);
            return "checkoutSuccess";
        }
    }
}
