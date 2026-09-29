package com.gaurav.internship.ems.model;

import com.gaurav.internship.ems.salary.InternSalaryCalculator;

public class Intern extends Employee {

    public Intern(int id, String name, String email, double stipend) {
        super(id, name, email, new InternSalaryCalculator(stipend));
    }

    @Override
    public String getEmployeeType() {
        return "Intern";
    }
}
