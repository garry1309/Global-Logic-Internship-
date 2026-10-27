package com.gaurav.internship.employeeapi.service;

import com.gaurav.internship.employeeapi.dto.EmployeeRequest;
import com.gaurav.internship.employeeapi.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse create(EmployeeRequest request);
    EmployeeResponse update(Long id, EmployeeRequest request);
    void delete(Long id);
    EmployeeResponse getById(Long id);
    List<EmployeeResponse> getAll();
}
