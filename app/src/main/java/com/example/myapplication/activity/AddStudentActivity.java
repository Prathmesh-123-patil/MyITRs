package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myapplication.database.FirebaseDatabaseHelper;
import com.example.myapplication.R;

public class AddStudentActivity extends AppCompatActivity {

    EditText etStudentName, etRollNumber, etDepartment, etDivision;
    Button btnAddStudent;
    FirebaseDatabaseHelper dbHelper;  // Replace SQLite helper with Firebase helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Initialize views
        etStudentName = findViewById(R.id.etStudentName);
        etRollNumber = findViewById(R.id.etRollNumber);
        etDepartment = findViewById(R.id.etDepartment);
        etDivision = findViewById(R.id.etDivision);
        btnAddStudent = findViewById(R.id.btnAddStudent);

        dbHelper = new FirebaseDatabaseHelper();

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etStudentName.getText().toString().trim();
                String roll = etRollNumber.getText().toString().trim();
                String dept = etDepartment.getText().toString().trim();
                String div = etDivision.getText().toString().trim();

                if (name.isEmpty() || roll.isEmpty() || dept.isEmpty() || div.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.addStudent(name, roll, dept, div, new FirebaseDatabaseHelper.AddStudentCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(AddStudentActivity.this, "Student added successfully", Toast.LENGTH_SHORT).show();

                            // Clear fields
                            etStudentName.setText("");
                            etRollNumber.setText("");
                            etDepartment.setText("");
                            etDivision.setText("");
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(AddStudentActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
