package com.gaurav.internship.employeeapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

// REQUEST DTO: what the client sends for Add (POST) and Update (PUT).
// The validation annotations are enforced because the controller uses @Valid.
public record EmployeeRequest(

        @Schema(example = "Gaurav Pratap")
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @Schema(example = "gaurav@example.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid email address")
        String email,

        @Schema(example = "Engineering")
        @NotBlank(message = "Department is required")
        String department,

        @Schema(example = "55000")
        @NotNull(message = "Salary is required")
        @Positive(message = "Salary must be greater than zero")
        BigDecimal salary
) {}
