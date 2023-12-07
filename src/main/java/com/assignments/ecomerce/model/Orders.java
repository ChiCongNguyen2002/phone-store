package com.assignments.ecomerce.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name="Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private Date orderDate;
    private Integer status;
    private Integer couponId;
    private String paymentMethod;
    private Double total;
    private String ShipName;
    private String ShipAddress;
    private String ShipPhoneNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setShipName(String shipName) {
        ShipName = shipName;
    }

    public void setShipAddress(String shipAddress) {
        ShipAddress = shipAddress;
    }

    public void setShipPhoneNumber(String shipPhone) {
        ShipPhoneNumber = shipPhone;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Orders(Integer id, Customer customer, Employee employee, Date orderDate, Integer status, Integer couponId, String paymentMethod, Double total, String shipName, String shipAddress, String shipPhoneNumber, List<OrderDetail> orderDetails) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.orderDate = orderDate;
        this.status = status;
        this.couponId = couponId;
        this.paymentMethod = paymentMethod;
        this.total = total;
        ShipName = shipName;
        ShipAddress = shipAddress;
        ShipPhoneNumber = shipPhoneNumber;
        this.orderDetails = orderDetails;
    }

    public Orders(){}
}
