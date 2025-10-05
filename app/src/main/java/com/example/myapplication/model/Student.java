package com.example.myapplication.model;

public class Student {
    public String id;
    public String studentName;
    public String rollNumber;
    public String department;
    public String division;

    public Student() {}
    public Student(String id, String name, String roll, String dept, String div) {
        this.id = id;
        this.studentName = name;
        this.rollNumber = roll;
        this.department = dept;
        this.division = div;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public String getRollNumber() { return rollNumber; }

    public String getDepartment() { return department; }
    public String getDivision() { return division; }
}
