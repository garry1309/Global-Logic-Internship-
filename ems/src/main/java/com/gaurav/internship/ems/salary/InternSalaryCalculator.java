package com.gaurav.internship.ems.salary;

public class InternSalaryCalculator implements SalaryCalculator {

    private final double stipend;

    public InternSalaryCalculator(double stipend) {
        this.stipend = stipend;
    }

    @Override
    public double calculate() {
        return stipend;
    }
}
