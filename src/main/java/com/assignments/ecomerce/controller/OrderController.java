package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CustomerService customerService;
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
    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private CouponService couponService;

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
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user1", user.getId());
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
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user1", user.getId());
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
                                    @RequestParam("idEmployee") Integer idEmployee,
                                    @PathVariable("id") Integer id, Model model) throws MessagingException, UnsupportedEncodingException {
        Orders order = orderService.getById(id);
        int status = order.getStatus();
        switch (status) {
            case 1:
                order.setStatus(2);
                order.setShipName(order.getShipName());
                order.setShipPhoneNumber(order.getShipPhoneNumber());
                User user = userService.findByIdAdmin(idEmployee);
                order.setAdminName(user.getFullname());
                order.setEmployee(null);
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
                        return "Sản phẩm với ID: " + product.getId() + " không đủ số lượng. \n\n Vui lòng kiểm tra số lượng sản phẩm trước khi xác nhận ";
                    }
                    product.setQuantity(newQuan);
                    productService.updateQuantity(product);
                }
                //send email confirm
                emailService.sendEmailConfirmOrder(order.getCustomer().getEmail(),
                        "Order Confirmation",
                        order.getCustomer().getFullname(),
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
                                          @PathVariable("id") Integer id, Model model, Principal principal) throws MessagingException, UnsupportedEncodingException {
        Orders order = orderService.getById(id);
        order.setStatus(5);
        orderService.save(order);
        emailService.sendEmailCancelOrder(order.getCustomer().getEmail(), "Order Cancel", order.getCustomer().getFullname());
        User user = userService.findByEmailUser(principal.getName());
        Customer customer = customerService.findByEmailCustomer(principal.getName());
        List<Category> categories = categoryService.getAllCategory();
        Page<Orders> listOrder = orderService.pageOrdersById(pageNo, customer.getId());
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

        model.addAttribute("userDetail", user);
        model.addAttribute("categories", categories);
        model.addAttribute("name", userDetails);
        model.addAttribute("userId", user.getId());
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        model.addAttribute("userDetail", user);
        model.addAttribute("userDetails", userDetails);
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
        Page<Orders> listOrder = orderService.searchOrdersByTimeEmployee(pageNo, dateFrom, dateTo);
        model.addAttribute("user", userDetails);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        return "OrderManager";
    }

    @GetMapping("/UpdateOrderStatusByEmployee/{pageNo}/{id}")
    public String UpdateOrderStatusByEmployee(@PathVariable("pageNo") int pageNo,
                                              @PathVariable("id") Integer id,
                                              @RequestParam("idEmployee") Integer idEmployee,
                                              Model model, Principal principal) throws MessagingException, UnsupportedEncodingException {
        Orders order = orderService.getById(id);
        int status = order.getStatus();
        switch (status) {
            case 1:
                order.setStatus(2);
                order.setShipName(order.getShipName());
                order.setShipPhoneNumber(order.getShipPhoneNumber());
                Employee employee = employeeService.findIdByUser(idEmployee);
                User user = userService.findByIdAdmin(idEmployee); //ADMIN
                order.setEmployee(employee);
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
                        return "Sản phẩm với ID: " + product.getId() + " không đủ số lượng. \n\n Vui lòng kiểm tra số lượng sản phẩm trước khi xác nhận ";
                    }
                    product.setQuantity(newQuan);
                    productService.updateQuantity(product);
                }
                //send email confirm
                emailService.sendEmailConfirmOrder(order.getCustomer().getEmail(),
                        "Order Confirmation",
                        order.getCustomer().getFullname(),
                        orderDetails);
                orderService.save(order);
                break;
            case 2:
                order.setStatus(3);
                Employee employee1 = employeeService.findIdByUser(idEmployee);
                order.setEmployee(employee1);
                orderService.save(order);
                break;
            case 3:
                order.setStatus(4);
                Employee employee2 = employeeService.findIdByUser(idEmployee);
                order.setEmployee(employee2);
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

    @PostMapping("/checkout")
    public String pageCheckOut(@RequestParam(value = "coupon", required = false) String coupon, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDetails userDetails = null;
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            User user = userService.findByEmail(principal.getName());
            List<Category> categories = categoryService.getAllCategory();
            model.addAttribute("coupon", coupon);
            model.addAttribute("categories", categories);
            model.addAttribute("userId", user.getId());
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("name", userDetails);
            model.addAttribute("userDetails", userDetails);
            model.addAttribute("user", user);
            return "checkoutUser";
        }
    }

    @PostMapping("/checkoutSuccess")
    public String pageCheckOutUser(@RequestParam(value = "coupon", required = false) String coupon, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            UserDetails userDetails = null;
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            User user = userService.findByEmail(principal.getName());
            Customer customer = customerService.findByEmailCustomer(principal.getName());
            List<Category> categories = categoryService.getAllCategory();
            List<CartDetail> listCartDetail = cartDetailService.findByUserId(user.getId());
            List<CartDetail> cartDetail = cartDetailService.findByUserId(user.getId());
            List<OrderDetail> listOrderDetail = new ArrayList<>();
            Employee noEmployee = new Employee();
            noEmployee.setId(-1);
            Orders order = new Orders();
            order.setEmployee(noEmployee);
            order.setCustomer(customer);
            order.setPaymentMethod("COD");
            order.setAdminName("-1");
            Date currentDate = new Date(System.currentTimeMillis());
            order.setOrderDate(currentDate);

            double sum = 0;
            Double promoPrice = 0.0d;
            for (CartDetail object : cartDetail) {
                sum += object.getProduct().getPrice() * object.getQuantity();
            }

            if (coupon != null) {
                Coupon coupon1 = couponService.findByCode(coupon);
                order.setCouponId(coupon1.getId());
                promoPrice = coupon1.getPromotion() * sum / 100;
                order.setTotal(sum - promoPrice);
            } else {
                order.setCouponId(null);
                order.setTotal(sum);
            }

            order.setShipAddress(customer.getAddress());
            order.setShipName(customer.getFullname());
            order.setShipPhoneNumber(customer.getPhone());
            order.setStatus(1);

            Integer orderId = orderService.saveOrderPay(order);
            for (CartDetail o : cartDetail) {
                OrderDetail orderDetail = new OrderDetail();
                OrderDetailId orderDetailId = new OrderDetailId();
                orderDetailId.setOrderId(orderId);
                orderDetailId.setProductId(o.getProductId());
                orderDetail.setId(orderDetailId);
                orderDetail.setQuantity(o.getQuantity());
                orderDetail.setUnitPrice(o.getUnitPrice());
                listOrderDetail.add(orderDetail);
            }
            for (OrderDetail orderDetail : listOrderDetail) {
                orderDetailService.saveOrderDetail(orderId, orderDetail.getId().getProductId(), orderDetail.getQuantity(), orderDetail.getUnitPrice());
            }

            model.addAttribute("coupon", coupon);
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
