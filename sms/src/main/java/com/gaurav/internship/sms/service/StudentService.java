package com.gaurav.internship.sms.service;

import com.gaurav.internship.sms.exception.StudentNotFoundException;
import com.gaurav.internship.sms.model.Student;

import java.util.List;

// ABSTRACTION: defines the contract for student operations, independent of storage details
public interface StudentService {
    void addStudent(Student student);
    List<Student> viewAllStudents();
    Student searchStudent(int id) throws StudentNotFoundException;
    void updateStudent(int id, String name, int age, String course, double grade) throws StudentNotFoundException;
    void deleteStudent(int id) throws StudentNotFoundException;
}
