package com.gaurav.internship.ems.notification;

import com.gaurav.internship.ems.model.Employee;

// ISP: a tiny, focused interface — just one method.
// DIP: EmployeeService depends on this interface, not on a concrete
// EmailNotificationService/SmsNotificationService.
public interface NotificationService {
    void notify(Employee employee, String message);
}
