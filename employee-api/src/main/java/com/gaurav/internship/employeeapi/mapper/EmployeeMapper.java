package com.gaurav.internship.employeeapi.mapper;

import com.gaurav.internship.employeeapi.dto.EmployeeRequest;
import com.gaurav.internship.employeeapi.dto.EmployeeResponse;
import com.gaurav.internship.employeeapi.model.Employee;
import org.springframework.stereotype.Component;

// Converts between DTOs and the Employee model, so the service doesn't mix
// mapping details into business logic (Single Responsibility).
@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {
        return new Employee(null, request.name(), request.email(),
                request.department(), request.salary());
    }

    public void updateEntity(Employee employee, EmployeeRequest request) {
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setDepartment(request.department());
        employee.setSalary(request.salary());
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(employee.getId(), employee.getName(),
                employee.getEmail(), employee.getDepartment(), employee.getSalary());
    }
}
