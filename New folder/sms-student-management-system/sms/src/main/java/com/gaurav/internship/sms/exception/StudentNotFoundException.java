package com.gaurav.internship.sms.exception;

// Custom checked exception — forces callers to handle the "student doesn't exist" case
public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
