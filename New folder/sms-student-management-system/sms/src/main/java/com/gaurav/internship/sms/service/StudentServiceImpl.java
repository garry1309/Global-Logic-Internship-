package com.gaurav.internship.sms.service;

import com.gaurav.internship.sms.exception.StudentNotFoundException;
import com.gaurav.internship.sms.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// implements StudentService: provides the real logic behind the abstract contract
public class StudentServiceImpl implements StudentService {

    // COLLECTIONS: HashMap gives O(1) lookup by student ID
    private final Map<Integer, Student> studentMap = new HashMap<>();

    @Override
    public void addStudent(Student student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public List<Student> viewAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    @Override
    public Student searchStudent(int id) throws StudentNotFoundException {
        Student student = studentMap.get(id);
        if (student == null) {
            throw new StudentNotFoundException("No student found with ID: " + id);
        }
        return student;
    }

    @Override
    public void updateStudent(int id, String name, int age, String course, double grade)
            throws StudentNotFoundException {
        Student student = searchStudent(id); // reuses search, throws if not found
        student.setName(name);
        student.setAge(age);
        student.setCourse(course);
        student.setGrade(grade);
    }

    @Override
    public void deleteStudent(int id) throws StudentNotFoundException {
        if (!studentMap.containsKey(id)) {
            throw new StudentNotFoundException("No student found with ID: " + id);
        }
        studentMap.remove(id);
    }
}
