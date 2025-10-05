package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.database.FirebaseDatabaseHelper;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.AttendanceRecord;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MarkAttendanceActivity extends AppCompatActivity {

    EditText etDept, etDiv, etDate;
    Button btnLoadStudents, btnSaveAttendance;
    ListView listStudents;
    FirebaseDatabaseHelper dbHelper;

    ArrayList<String> studentList = new ArrayList<>();
    HashMap<String, String> attendanceMap = new HashMap<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        etDept = findViewById(R.id.etDept);
        etDiv = findViewById(R.id.etDiv);
        etDate = findViewById(R.id.etDate);
        btnLoadStudents = findViewById(R.id.btnLoadStudents);
        btnSaveAttendance = findViewById(R.id.btnSaveAttendance);
        listStudents = findViewById(R.id.listStudents);

        dbHelper = new FirebaseDatabaseHelper();

        // Load Students Button
        btnLoadStudents.setOnClickListener(v -> {
            String dept = etDept.getText().toString().trim();
            String div = etDiv.getText().toString().trim();

            if (dept.isEmpty() || div.isEmpty()) {
                Toast.makeText(MarkAttendanceActivity.this, "Enter Department & Division", Toast.LENGTH_SHORT).show();
                return;
            }

            studentList.clear();
            attendanceMap.clear();

            dbHelper.getAllStudents(dept, div, students -> {
                if (students.isEmpty()) {
                    Toast.makeText(MarkAttendanceActivity.this, "No students found", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (Student student : students) {
                    studentList.add(student.getRollNumber() + " - " + student.getStudentName() + " (P)"); // default Present
                    attendanceMap.put(student.getRollNumber(), "Present");
                }

                adapter = new ArrayAdapter<>(MarkAttendanceActivity.this,
                        android.R.layout.simple_list_item_1, studentList);
                listStudents.setAdapter(adapter);
            });
        });

        // Toggle Attendance on ListView item click
        listStudents.setOnItemClickListener((parent, view, position, id) -> {
            String item = studentList.get(position);
            String roll = item.split(" - ")[0];
            String name = item.split(" - ")[1].split(" \\(")[0];

            if (attendanceMap.get(roll).equals("Present")) {
                attendanceMap.put(roll, "Absent");
                studentList.set(position, roll + " - " + name + " (A)");
            } else {
                attendanceMap.put(roll, "Present");
                studentList.set(position, roll + " - " + name + " (P)");
            }

            adapter.notifyDataSetChanged();
        });

        // Save Attendance Button
        btnSaveAttendance.setOnClickListener(v -> {
            String dept = etDept.getText().toString().trim();
            String div = etDiv.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (date.isEmpty()) {
                Toast.makeText(MarkAttendanceActivity.this, "Enter Date", Toast.LENGTH_SHORT).show();
                return;
            }

            for (String item : studentList) {
                String roll = item.split(" - ")[0];
                String name = item.split(" - ")[1].split(" \\(")[0];
                String status = attendanceMap.get(roll);

                dbHelper.markAttendance(name, roll, dept, div, date, status);
            }

            Toast.makeText(MarkAttendanceActivity.this, "Attendance Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
