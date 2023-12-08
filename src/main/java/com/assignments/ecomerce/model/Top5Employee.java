package com.assignments.ecomerce.model;
import lombok.Getter;

@Getter
public class Top5Employee {
    private String fullname;
    private Double total;
    private Long sumQuantity;

    public Top5Employee(String fullname, Double total, Long  sumQuantity) {
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

    public void setSumQuantity(Long  sumQuantity) {
        this.sumQuantity = sumQuantity;
    }
}
