package com.gaurav.internship.ems.model;

import com.gaurav.internship.ems.salary.PartTimeSalaryCalculator;

public class PartTimeEmployee extends Employee {

    public PartTimeEmployee(int id, String name, String email, double hourlyRate, double hoursWorked) {
        super(id, name, email, new PartTimeSalaryCalculator(hourlyRate, hoursWorked));
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }
}
