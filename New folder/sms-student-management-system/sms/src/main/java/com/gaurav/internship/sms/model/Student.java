package com.gaurav.internship.sms.model;

// ENCAPSULATION: private fields, controlled access via getters/setters
public class Student {

    private int id;
    private String name;
    private int age;
    private String course;
    private double grade;

    public Student(int id, String name, int age, String course, double grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0 || age > 100) {
            throw new IllegalArgumentException("Age must be between 1 and 100");
        }
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d Name: %-15s Age: %-3d Course: %-15s Grade: %.2f",
                id, name, age, course, grade);
    }
}
