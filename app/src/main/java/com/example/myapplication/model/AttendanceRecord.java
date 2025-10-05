package com.example.myapplication.model;

public class AttendanceRecord {
    public String id;
    public String student_name;
    public String roll_number;
    public String department;
    public String division;
    public String date;
    public String status;

    public AttendanceRecord() {}
    public AttendanceRecord(String id, String name, String roll, String dept, String div,
                            String date, String status) {
        this.id = id;
        this.student_name = name;
        this.roll_number = roll;
        this.department = dept;
        this.division = div;
        this.date = date;
        this.status = status;
    }

    // Getters
    public String getId() { return id; }
    public String getStudent_name() { return student_name; }
    public String getRoll_number() { return roll_number; }
    public String getDepartment() { return department; }
    public String getDivision() { return division; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
}