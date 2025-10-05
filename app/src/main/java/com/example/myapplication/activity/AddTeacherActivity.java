package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.database.FirebaseDatabaseHelper;
import com.example.myapplication.R;

public class AddTeacherActivity extends AppCompatActivity {

    EditText etName, etUsername, etPassword;
    Button btnSave;
    FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        dbHelper = new FirebaseDatabaseHelper();

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AddTeacherActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.addTeacher(name, username, password, new FirebaseDatabaseHelper.AddTeacherCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddTeacherActivity.this, "Teacher Added", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(AddTeacherActivity.this, "Failed to Add: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
