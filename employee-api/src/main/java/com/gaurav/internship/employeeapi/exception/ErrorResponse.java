package com.gaurav.internship.employeeapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

// One consistent JSON shape for every error the API returns.
// fieldErrors is only present for validation failures (null fields are omitted).
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {}
