package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class DashboardActivity extends AppCompatActivity {

    Button btnAddTeacher, btnViewTeacher, btnAddStudent, btnMarkAttendance, btnViewAttendance;
    String role; // admin or teacher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        btnViewTeacher = findViewById(R.id.btnViewTeacher);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnMarkAttendance = findViewById(R.id.btnMarkAttendance);
        btnViewAttendance = findViewById(R.id.btnViewAttendance);

        // ✅ Get role from LoginActivity
        role = getIntent().getStringExtra("role");

        if ("admin".equals(role)) {
            // Admin options
            btnAddTeacher.setVisibility(Button.VISIBLE);
            btnViewTeacher.setVisibility(Button.VISIBLE);
            btnAddStudent.setVisibility(Button.VISIBLE);
            btnViewAttendance.setVisibility(Button.VISIBLE);
            btnMarkAttendance.setVisibility(Button.GONE);

        } else if ("teacher".equals(role)) {
            // Teacher options
            btnMarkAttendance.setVisibility(Button.VISIBLE);
            btnAddStudent.setVisibility(Button.VISIBLE);
            btnViewAttendance.setVisibility(Button.VISIBLE);
            btnAddTeacher.setVisibility(Button.GONE);
            btnViewTeacher.setVisibility(Button.GONE);
        }

        // ✅ Listeners
        btnAddTeacher.setOnClickListener(v -> startActivity(new Intent(this, AddTeacherActivity.class)));
        btnViewTeacher.setOnClickListener(v -> startActivity(new Intent(this, ViewTeacherActivity.class)));
        btnAddStudent.setOnClickListener(v -> startActivity(new Intent(this, AddStudentActivity.class)));
        btnMarkAttendance.setOnClickListener(v -> startActivity(new Intent(this, MarkAttendanceActivity.class)));
        btnViewAttendance.setOnClickListener(v -> startActivity(new Intent(this, AttendanceReportActivity.class)));
    }
}