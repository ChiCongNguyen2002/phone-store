package com.assignments.ecomerce.model;

import lombok.Getter;

@Getter
public class WeeklyRevenue {
    private Double sumTotal;
    private String weekDate;
    private Double mondayTotal;
    private Double tuesdayTotal;
    private Double wednesdayTotal;
    private Double thursdayTotal;
    private Double fridayTotal;
    private Double saturdayTotal;
    private Double sundayTotal;

    public WeeklyRevenue(Double sumTotal,String weekDate, Double mondayTotal, Double tuesdayTotal, Double wednesdayTotal,
                         Double thursdayTotal, Double fridayTotal, Double saturdayTotal, Double sundayTotal) {
        this.sumTotal = sumTotal;
        this.weekDate = weekDate;
        this.mondayTotal = mondayTotal;
        this.tuesdayTotal = tuesdayTotal;
        this.wednesdayTotal = wednesdayTotal;
        this.thursdayTotal = thursdayTotal;
        this.fridayTotal = fridayTotal;
        this.saturdayTotal = saturdayTotal;
        this.sundayTotal = sundayTotal;
    }

    public void setSumTotal(Double sumTotal) {
        this.sumTotal = sumTotal;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public void setMondayTotal(Double mondayTotal) {
        this.mondayTotal = mondayTotal;
    }

    public void setTuesdayTotal(Double tuesdayTotal) {
        this.tuesdayTotal = tuesdayTotal;
    }

    public void setWednesdayTotal(Double wednesdayTotal) {
        this.wednesdayTotal = wednesdayTotal;
    }

    public void setThursdayTotal(Double thursdayTotal) {
        this.thursdayTotal = thursdayTotal;
    }

    public void setFridayTotal(Double fridayTotal) {
        this.fridayTotal = fridayTotal;
    }

    public void setSaturdayTotal(Double saturdayTotal) {
        this.saturdayTotal = saturdayTotal;
    }

    public void setSundayTotal(Double sundayTotal) {
        this.sundayTotal = sundayTotal;
    }
}
