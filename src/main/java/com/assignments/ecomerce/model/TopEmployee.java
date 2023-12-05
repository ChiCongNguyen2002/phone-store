package com.assignments.ecomerce.model;

import java.math.BigDecimal;

public class TopEmployee {
    private String fullname;
    private Double total;
    private Long sumQuantity;

    public TopEmployee(String fullname, Double total, Long sumQuantity) {
        this.fullname = fullname;
        this.total = total;
        this.sumQuantity = sumQuantity;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setSumQuantity(Long sumQuantity) {
        this.sumQuantity = sumQuantity;
    }
}
