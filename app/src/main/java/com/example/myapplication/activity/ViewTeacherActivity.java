package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.database.FirebaseDatabaseHelper;
import com.example.myapplication.model.Teacher;

public class ViewTeacherActivity extends AppCompatActivity {

    FirebaseDatabaseHelper dbHelper;
    LinearLayout teacherListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher);

        dbHelper = new FirebaseDatabaseHelper();
        teacherListLayout = findViewById(R.id.teacherListLayout);

        loadTeachers();
    }

    private void loadTeachers() {
        dbHelper.getTeachers(teacherList -> {
            teacherListLayout.removeAllViews(); // Clear previous views

            if (teacherList.isEmpty()) {
                TextView tv = new TextView(ViewTeacherActivity.this);
                tv.setText("No Teachers Found.");
                tv.setTextSize(18f);
                teacherListLayout.addView(tv);
                return;
            }

            for (Teacher teacher : teacherList) {
                TextView tv = new TextView(ViewTeacherActivity.this);
                tv.setText("* Name: " + teacher.getName() +
                        "  |  Username: " + teacher.getUsername());
                tv.setTextSize(18f);
                tv.setPadding(8, 8, 8, 8);
                teacherListLayout.addView(tv);
            }
        });
    }
}
