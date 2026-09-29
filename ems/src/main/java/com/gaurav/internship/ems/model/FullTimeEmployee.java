package com.gaurav.internship.ems.model;

import com.gaurav.internship.ems.salary.FullTimeSalaryCalculator;

public class FullTimeEmployee extends Employee {

    public FullTimeEmployee(int id, String name, String email, double monthlySalary) {
        super(id, name, email, new FullTimeSalaryCalculator(monthlySalary));
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
}
