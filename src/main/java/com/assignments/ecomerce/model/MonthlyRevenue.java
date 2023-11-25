package com.assignments.ecomerce.model;

import lombok.Getter;

@Getter
public class MonthlyRevenue {
    private int month;
    private Double sumTotal;

    public MonthlyRevenue(int month, Double sumTotal) {
        this.month = month;
        this.sumTotal = sumTotal;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setSumTotal(Double sumTotal) {
        this.sumTotal = sumTotal;
    }
}
