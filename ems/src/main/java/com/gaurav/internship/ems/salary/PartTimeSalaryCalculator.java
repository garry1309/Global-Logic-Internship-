package com.gaurav.internship.ems.salary;

public class PartTimeSalaryCalculator implements SalaryCalculator {

    private final double hourlyRate;
    private final double hoursWorked;

    public PartTimeSalaryCalculator(double hourlyRate, double hoursWorked) {
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculate() {
        return hourlyRate * hoursWorked;
    }
}
