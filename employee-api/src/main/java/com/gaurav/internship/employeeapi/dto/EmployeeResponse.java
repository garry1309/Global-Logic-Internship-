package com.gaurav.internship.employeeapi.dto;

import java.math.BigDecimal;

// RESPONSE DTO: what the API returns. Includes the generated id.
public record EmployeeResponse(
        Long id,
        String name,
        String email,
        String department,
        BigDecimal salary
) {}
