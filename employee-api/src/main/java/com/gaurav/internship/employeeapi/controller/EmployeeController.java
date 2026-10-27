package com.gaurav.internship.employeeapi.controller;

import com.gaurav.internship.employeeapi.dto.EmployeeRequest;
import com.gaurav.internship.employeeapi.dto.EmployeeResponse;
import com.gaurav.internship.employeeapi.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// @RestController = @Controller + @ResponseBody: return values are written as JSON.
// The controller only handles HTTP concerns (paths, status codes, validation trigger).
// All business logic lives in the service.
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Add, update, delete and fetch employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Constructor injection - Spring supplies the EmployeeService bean.
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Add a new employee")
    @PostMapping
    public ResponseEntity<EmployeeResponse> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse created = employeeService.create(request);
        // 201 Created + Location header pointing at the new resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAll();
    }

    @Operation(summary = "Get an employee by id")
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @Operation(summary = "Update an existing employee")
    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable Long id,
                                           @Valid @RequestBody EmployeeRequest request) {
        return employeeService.update(id, request);
    }

    @Operation(summary = "Delete an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build(); // 204, no body
    }
}
