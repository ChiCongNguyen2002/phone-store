package com.assignments.ecomerce.service.impl;

import com.assignments.ecomerce.model.*;
import com.assignments.ecomerce.repository.OrderRepository;
import com.assignments.ecomerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Orders save(Orders orders) {
        return orderRepository.save(orders);
    }

    public Orders findById(Integer id) {
        return orderRepository.findById(id).get();
    }

    public List<Orders> getAllOrders() {
        return (List<Orders>) orderRepository.findAll();
    }

    public Orders getOrderById(Integer id) {
        Optional<Orders> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    public int countOrders() {
        return orderRepository.countOrders();
    }

    public Page<Orders> pageOrders(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        return orderRepository.pageOrders(pageable);
    }

    @Override
    public Page<Orders> pageOrdersById(int pageNo, Integer userId) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        return orderRepository.pageOrdersById(pageable, userId);
    }

    public Page<Orders> searchOrders(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Orders> order = transfer(orderRepository.searchOrders(keyword.trim()));
        Page<Orders> orderPages = toPage(order, pageable);
        return orderPages;
    }

    public Page toPage(List<Orders> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size()) ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Orders> transfer(List<Orders> orders) {
        List<Orders> orderList = new ArrayList<>();
        for (Orders order : orders) {
            Orders newOrder = new Orders();
            newOrder.setId(order.getId());
            newOrder.setCustomer(order.getUser());
            newOrder.setOrderDate(order.getOrderDate());
            newOrder.setStatus(order.getStatus());
            newOrder.setCouponId(order.getCouponId());
            newOrder.setPaymentMethod(order.getPaymentMethod());
            newOrder.setTotal(order.getTotal());
            orderList.add(newOrder);
        }
        return orderList;
    }

    public List<Object> getData(Date dateFrom, Date dateTo, int year, String chartType) {
        switch (chartType) {
            case "top5Employees":
                List<Object[]> resultEmployee = orderRepository.getTop5EmployeesWithSumQuantity(dateFrom, dateTo);
                List<TopCustomer> top5Employee = new ArrayList<>();
                for (Object[] result : resultEmployee) {
                    String fullname = (String) result[0];
                    Double total = (Double) result[1];
                    Long sumQuantity = (Long) result[2];
                    TopCustomer topEmployee = new TopCustomer(fullname, total, sumQuantity);
                    top5Employee.add(topEmployee);
                }
                return new ArrayList<>(top5Employee);
            case "top5Users":
                List<Object[]> results = orderRepository.getTop5UsersWithSumQuantity(dateFrom, dateTo);
                List<TopCustomer> tops = new ArrayList<>();
                for (Object[] result : results) {
                    String fullname = (String) result[0];
                    Double total = (Double) result[1];
                    Long sumQuantity = (Long) result[2];
                    TopCustomer topCustomer = new TopCustomer(fullname, total, sumQuantity);
                    tops.add(topCustomer);
                }
                return new ArrayList<>(tops);
            case "top10Products":
                List<Object[]> resultProduct = orderRepository.getTop10ProductsWithSumQuantity(dateFrom, dateTo);
                List<Product> products = new ArrayList<>();
                for (Object[] result : resultProduct) {
                    String name = (String) result[0];
                    Double price = (Double) result[1];
                    String image = (String) result[2];
                    Product product = new Product(name, price, image);
                    products.add(product);
                }
                return new ArrayList<>(products);
            case "monthlyRevenue":
                List<Object[]> resultMonth = orderRepository.getMonthlyRevenue(dateFrom, dateTo, year);
                List<MonthlyRevenue> monthlyRevenues = new ArrayList<>();

                for (Object[] result : resultMonth) {
                    Integer month = (Integer) result[0];
                    Double sumTotal = (Double) result[2];
                    MonthlyRevenue monthlyRevenue = new MonthlyRevenue(month, sumTotal);
                    monthlyRevenues.add(monthlyRevenue);
                }
                return new ArrayList<>(monthlyRevenues);
            case "weeklyRevenue":
                List<Object[]> result = orderRepository.getWeeklyRevenue(dateFrom, dateTo);
                List<WeeklyRevenue> weeklyRevenues = new ArrayList<>();

                for (Object[] row : result) {
                    String weekDate = (String) row[0];
                    Double mondayTotal = (Double) row[1];
                    Double tuesdayTotal = (Double) row[2];
                    Double wednesdayTotal = (Double) row[3];
                    Double thursdayTotal = (Double) row[4];
                    Double fridayTotal = (Double) row[5];
                    Double saturdayTotal = (Double) row[6];
                    Double sundayTotal = (Double) row[7];
                    WeeklyRevenue weeklyRevenue = new WeeklyRevenue(weekDate, mondayTotal, tuesdayTotal, wednesdayTotal, thursdayTotal, fridayTotal, saturdayTotal, sundayTotal);
                    weeklyRevenues.add(weeklyRevenue);
                }
                return new ArrayList<>(weeklyRevenues);
            default:
                throw new IllegalArgumentException("Invalid chart type: " + chartType);
        }
    }

    public Page<Orders> searchOrdersByTime(int pageNo, Date dateFrom, Date dateTo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        List<Orders> order = transfer(orderRepository.searchOrdersByTime(dateFrom, dateTo));
        Page<Orders> orderPages = toPage(order, pageable);
        return orderPages;
    }

    public List<Orders> searchOrdersByTimeToExcel(Date dateFrom, Date dateTo) {
        List<Orders> order = orderRepository.searchOrdersByTime(dateFrom, dateTo);
        return order;
    }

    @Override
    public Orders getById(Integer id) {
        Orders order = orderRepository.getById(id);
        Orders newOrder = new Orders();
        newOrder.setId(order.getId());
        newOrder.setCouponId(order.getCouponId());
        newOrder.setCustomer(order.getUser());
        newOrder.setOrderDate(order.getOrderDate());
        newOrder.setStatus(order.getStatus());
        newOrder.setTotal(order.getTotal());
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setShipAddress(order.getShipAddress());
        newOrder.setShipPhoneNumber(order.getShipPhoneNumber());
        newOrder.setShipName(order.getShipName());
        return newOrder;
    }
}
