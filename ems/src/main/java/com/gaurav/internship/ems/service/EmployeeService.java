package com.gaurav.internship.ems.service;

import com.gaurav.internship.ems.model.Employee;
import com.gaurav.internship.ems.notification.NotificationService;
import com.gaurav.internship.ems.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;


public class EmployeeService {

    private final EmployeeRepository repository;
    private final NotificationService notificationService;

    public EmployeeService(EmployeeRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void addEmployee(Employee employee) {
        repository.save(employee); // "Save employee details" requirement
        System.out.println("Employee added: " + employee.getName());
    }

    public void displayEmployee(int id) {
        Optional<Employee> employee = repository.findById(id);
        employee.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No employee found with ID: " + id)
        );
    }

    public void displayAllEmployees() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        employees.forEach(System.out::println);
    }

    public double calculateSalary(int id) {
        return repository.findById(id)
                .map(Employee::calculateSalary)
                .orElseThrow(() -> new IllegalArgumentException("No employee found with ID: " + id));
    }

    public void notifyEmployee(int id, String message) {
        repository.findById(id).ifPresentOrElse(
                employee -> notificationService.notify(employee, message),
                () -> System.out.println("No employee found with ID: " + id)
        );
    }
}
