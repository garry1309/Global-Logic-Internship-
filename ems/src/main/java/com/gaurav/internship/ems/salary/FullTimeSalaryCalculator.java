package com.gaurav.internship.ems.salary;


public class FullTimeSalaryCalculator implements SalaryCalculator {

    private final double monthlySalary;

    public FullTimeSalaryCalculator(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculate() {
        return monthlySalary;
    }
}
