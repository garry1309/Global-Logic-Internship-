package com.gaurav.internship.sms.app;

import com.gaurav.internship.sms.exception.InvalidInputException;
import com.gaurav.internship.sms.exception.StudentNotFoundException;
import com.gaurav.internship.sms.model.Student;
import com.gaurav.internship.sms.service.StudentService;
import com.gaurav.internship.sms.service.StudentServiceImpl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final StudentService studentService = new StudentServiceImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> {
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please select 1-6.");
                }
            } catch (InvalidInputException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (StudentNotFoundException e) {
                System.out.println("Not found: " + e.getMessage());
            } catch (Exception e) {

                System.out.println("Unexpected error: " + e.getMessage());
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Student Management System =====");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    private static void addStudent() {
        int id = readInt("Enter ID: ");
        String name = readNonEmptyString("Enter Name: ");
        int age = readInt("Enter Age: ");
        String course = readNonEmptyString("Enter Course: ");
        double grade = readDouble("Enter Grade (0-100): ");

        Student student = new Student(id, name, age, course, grade);
        studentService.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        List<Student> students = studentService.viewAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void searchStudent() throws StudentNotFoundException {
        int id = readInt("Enter ID to search: ");
        Student student = studentService.searchStudent(id);
        System.out.println("Found: " + student);
    }

    private static void updateStudent() throws StudentNotFoundException {
        int id = readInt("Enter ID to update: ");
        String name = readNonEmptyString("Enter new Name: ");
        int age = readInt("Enter new Age: ");
        String course = readNonEmptyString("Enter new Course: ");
        double grade = readDouble("Enter new Grade (0-100): ");

        studentService.updateStudent(id, name, age, course, grade);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() throws StudentNotFoundException {
        int id = readInt("Enter ID to delete: ");
        studentService.deleteStudent(id);
        System.out.println("Student deleted successfully.");
    }

    

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid whole number.");
        }
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            if (value < 0 || value > 100) {
                throw new InvalidInputException("Grade must be between 0 and 100.");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid number.");
        }
    }

    private static String readNonEmptyString(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            throw new InvalidInputException("This field cannot be empty.");
        }
        return value;
    }
}
