package com.gaurav.internship.ems.notification;

import com.gaurav.internship.ems.model.Employee;


public class ConsoleNotificationService implements NotificationService {

    @Override
    public void notify(Employee employee, String message) {
        System.out.println("[Notification to " + employee.getEmail() + "]: " + message);
    }
}
