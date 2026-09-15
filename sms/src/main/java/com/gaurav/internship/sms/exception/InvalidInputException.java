package com.gaurav.internship.sms.exception;

// Custom unchecked exception for bad user input (invalid ID format, empty name, etc.)
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
