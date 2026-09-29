package com.gaurav.internship.ems.model;

import com.gaurav.internship.ems.salary.SalaryCalculator;


public abstract class Employee {

    protected final int id;
    protected final String name;
    protected final String email;
    private final SalaryCalculator salaryCalculator;

    protected Employee(int id, String name, String email, SalaryCalculator salaryCalculator) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salaryCalculator = salaryCalculator;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    // Every employee type gets its salary the same way — by asking its calculator.
    // Employee itself never contains an if/else or switch on employee type.
    public double calculateSalary() {
        return salaryCalculator.calculate();
    }

    public abstract String getEmployeeType();

    @Override
    public String toString() {
        return String.format("ID: %-4d Name: %-15s Email: %-25s Type: %-10s Salary: %.2f",
                id, name, email, getEmployeeType(), calculateSalary());
    }
}
