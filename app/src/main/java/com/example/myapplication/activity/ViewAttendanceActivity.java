package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ViewAttendanceActivity extends AppCompatActivity {

    EditText etDepartment, etDivision, etDate;
    Button btnViewAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        etDepartment = findViewById(R.id.etDepartment);
        etDivision = findViewById(R.id.etDivision);
        etDate = findViewById(R.id.etDate);
        btnViewAttendance = findViewById(R.id.btnViewAttendance);

        btnViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dept = etDepartment.getText().toString().trim();
                String div = etDivision.getText().toString().trim();
                String date = etDate.getText().toString().trim();

                if (dept.isEmpty() || div.isEmpty() || date.isEmpty()) {
                    Toast.makeText(ViewAttendanceActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ViewAttendanceActivity.this, AttendanceReportActivity.class);
                    intent.putExtra("department", dept);
                    intent.putExtra("division", div);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            }
        });
    }
}