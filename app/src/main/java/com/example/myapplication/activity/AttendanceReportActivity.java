package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.model.AttendanceRecord;
import com.example.myapplication.database.FirebaseDatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AttendanceReportActivity extends AppCompatActivity {

    EditText etDept, etDiv, etDate;
    Button btnLoadReport;
    ListView listReport;
    FirebaseDatabaseHelper dbHelper;
    ArrayList<String> reportList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);

        etDept = findViewById(R.id.etDept);
        etDiv = findViewById(R.id.etDiv);
        etDate = findViewById(R.id.etDate);
        btnLoadReport = findViewById(R.id.btnLoadReport);
        listReport = findViewById(R.id.listReport);

        dbHelper = new FirebaseDatabaseHelper();

        btnLoadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dept = etDept.getText().toString().trim();
                String div = etDiv.getText().toString().trim();
                String date = etDate.getText().toString().trim();

                if (dept.isEmpty() || div.isEmpty() || date.isEmpty()) {
                    Toast.makeText(AttendanceReportActivity.this, "Enter Department, Division & Date", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.getAttendance(dept, div, date, new FirebaseDatabaseHelper.AttendanceCallback() {
                    @Override
                    public void onSuccess(ArrayList<AttendanceRecord> attendanceList) {
                        if (attendanceList.isEmpty()) {
                            Toast.makeText(AttendanceReportActivity.this, "No records found", Toast.LENGTH_SHORT).show();
                            listReport.setAdapter(null);
                            return;
                        }

                        reportList = new ArrayList<>();
                        for (AttendanceRecord record : attendanceList) {
                            reportList.add(record.getRoll_number() + " - " + record.getStudent_name() + " : " + record.getStatus());
                        }

                        adapter = new ArrayAdapter<>(AttendanceReportActivity.this,
                                android.R.layout.simple_list_item_1, reportList);
                        listReport.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(AttendanceReportActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
