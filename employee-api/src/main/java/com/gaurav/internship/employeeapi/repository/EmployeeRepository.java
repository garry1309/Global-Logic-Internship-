package com.gaurav.internship.employeeapi.repository;

import com.gaurav.internship.employeeapi.model.Employee;

import java.util.List;
import java.util.Optional;

// The service depends on THIS interface, not on a concrete storage class
// (Dependency Inversion). A database-backed version could replace the
// in-memory one without touching the service.
public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(Long id);
    Optional<Employee> findByEmail(String email);
    List<Employee> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}
