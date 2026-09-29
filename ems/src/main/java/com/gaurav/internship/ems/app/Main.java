package com.gaurav.internship.ems.app;

import com.gaurav.internship.ems.model.Employee;
import com.gaurav.internship.ems.model.FullTimeEmployee;
import com.gaurav.internship.ems.model.Intern;
import com.gaurav.internship.ems.model.PartTimeEmployee;
import com.gaurav.internship.ems.notification.ConsoleNotificationService;
import com.gaurav.internship.ems.notification.NotificationService;
import com.gaurav.internship.ems.repository.EmployeeRepository;
import com.gaurav.internship.ems.repository.InMemoryEmployeeRepository;
import com.gaurav.internship.ems.service.EmployeeService;

import java.util.Scanner;

public class Main {


    private static final EmployeeRepository repository = new InMemoryEmployeeRepository();
    private static final NotificationService notificationService = new ConsoleNotificationService();
    private static final EmployeeService employeeService = new EmployeeService(repository, notificationService);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = readInt("Enter your choice: ");
                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> displayEmployee();
                    case 3 -> calculateSalary();
                    case 4 -> displayAllEmployees();
                    case 5 -> notifyEmployee();
                    case 6 -> {
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Employee Management System =====");
        System.out.println("1. Add Employee");
        System.out.println("2. Display Employee Details");
        System.out.println("3. Calculate Salary");
        System.out.println("4. Display All Employees");
        System.out.println("5. Send Notification to Employee");
        System.out.println("6. Exit");
    }

    private static void addEmployee() {
        int id = readInt("Enter ID: ");
        String name = readString("Enter Name: ");
        String email = readString("Enter Email: ");

        System.out.println("Employee type: 1=Full-Time  2=Part-Time  3=Intern");
        int type = readInt("Enter choice: ");

        Employee employee = switch (type) {
            case 1 -> {
                double monthlySalary = readDouble("Enter monthly salary: ");
                yield new FullTimeEmployee(id, name, email, monthlySalary);
            }
            case 2 -> {
                double hourlyRate = readDouble("Enter hourly rate: ");
                double hoursWorked = readDouble("Enter hours worked this month: ");
                yield new PartTimeEmployee(id, name, email, hourlyRate, hoursWorked);
            }
            case 3 -> {
                double stipend = readDouble("Enter monthly stipend: ");
                yield new Intern(id, name, email, stipend);
            }
            default -> throw new IllegalArgumentException("Invalid employee type selected.");
        };

        employeeService.addEmployee(employee);
    }

    private static void displayEmployee() {
        int id = readInt("Enter ID: ");
        employeeService.displayEmployee(id);
    }

    private static void calculateSalary() {
        int id = readInt("Enter ID: ");
        double salary = employeeService.calculateSalary(id);
        System.out.printf("Calculated salary: %.2f%n", salary);
    }

    private static void displayAllEmployees() {
        employeeService.displayAllEmployees();
    }

    private static void notifyEmployee() {
        int id = readInt("Enter ID: ");
        String message = readString("Enter notification message: ");
        employeeService.notifyEmployee(id, message);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid whole number.");
        }
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number.");
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException("This field cannot be empty.");
        }
        return value;
    }
}
