package com.example.myapplication.database;

import com.example.myapplication.model.AttendanceRecord;
import com.example.myapplication.model.Student;
import com.example.myapplication.model.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FirebaseDatabaseHelper {
    private final DatabaseReference rootRef;
    private final DatabaseReference studentsRef;
    private final DatabaseReference teachersRef;
    private final DatabaseReference attendanceRef;

    public FirebaseDatabaseHelper() {
        // Top-level node (optional name)
        rootRef = FirebaseDatabase.getInstance().getReference("attendance_system");
        // Reference to "students" node in Firebase Realtime Database
        studentsRef = FirebaseDatabase.getInstance().getReference("students");
        // Reference to "teachers" node in Firebase Realtime Database
        teachersRef = FirebaseDatabase.getInstance().getReference("teachers");
        attendanceRef = FirebaseDatabase.getInstance().getReference("attendance");
    }

    // ---------- TEACHER METHODS ----------
    // Add a teacher to Firebase
    public void addTeacher(String name, String username, String password, AddTeacherCallback callback) {
        String teacherId = teachersRef.push().getKey();

        if (teacherId == null) {
            callback.onFailure("Failed to generate teacher ID");
            return;
        }

        Teacher teacher = new Teacher(teacherId, name, username, password);

        teachersRef.child(teacherId).setValue(teacher)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // Callback interface
    public interface AddTeacherCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public DatabaseReference getTeachers() {
        return rootRef.child("teachers");
    }

    // Fetch all teachers
    public void getTeachers(TeachersCallback callback) {
        teachersRef.get().addOnCompleteListener(task -> {
            ArrayList<Teacher> teacherList = new ArrayList<>();
            if (task.isSuccessful() && task.getResult() != null) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Teacher teacher = snapshot.getValue(Teacher.class);
                    if (teacher != null) {
                        teacherList.add(teacher);
                    }
                }
            }
            callback.onResult(teacherList);
        });
    }

    public interface TeachersCallback {
        void onResult(ArrayList<Teacher> teacherList);
    }



    // ---------- STUDENT METHODS ----------

    // Method to add a student
    public void addStudent(String name, String roll, String dept, String div, AddStudentCallback callback) {
        // Generate a unique key for each student
        String studentId = studentsRef.push().getKey();

        if (studentId == null) {
            callback.onFailure("Failed to generate student ID");
            return;
        }

        Student student = new Student(studentId, name, roll, dept, div);

        studentsRef.child(studentId).setValue(student)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    // Callback interface for async operations
    public interface AddStudentCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    // Filter students by department & division
    public Query getAllStudents(String dept, String div) {
        return rootRef.child("students")
                .orderByChild("department")
                .equalTo(dept);
        // For filtering by division as well, filter on client side
    }







    // ---------- ATTENDANCE METHODS ----------
    // Fetch attendance by department, division, and date
    public void getAttendance(String dept, String div, String date, AttendanceCallback callback) {
        attendanceRef.orderByChild("date").equalTo(date)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        ArrayList<AttendanceRecord> result = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            AttendanceRecord record = data.getValue(AttendanceRecord.class);
                            if (record != null && record.getDepartment().equals(dept) && record.getDivision().equals(div)) {
                                result.add(record);
                            }
                        }
                        callback.onSuccess(result);
                    }

                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError error) {
                        callback.onFailure(error.getMessage());
                    }
                });
    }

    public interface AttendanceCallback {
        void onSuccess(ArrayList<AttendanceRecord> attendanceList);
        void onFailure(String errorMessage);
    }


    public DatabaseReference getAttendanceSummary() {
        // Fetch all attendance and summarize on the client side
        return rootRef.child("attendance");
    }

    // Get students by department & division
    public void getAllStudents(String dept, String div, StudentsCallback callback) {
        studentsRef.get().addOnCompleteListener(task -> {
            ArrayList<Student> result = new ArrayList<>();
            if (task.isSuccessful() && task.getResult() != null) {
                for (DataSnapshot data : task.getResult().getChildren()) {
                    Student student = data.getValue(Student.class);
                    if (student != null &&
                            student.getDepartment().equals(dept) &&
                            student.getDivision().equals(div)) {
                        result.add(student);
                    }
                }
            }
            callback.onResult(result);
        });
    }

    // Mark attendance
    public void markAttendance(String name, String roll, String dept, String div, String date, String status) {
        String id = attendanceRef.push().getKey();
        if (id == null) return;

        AttendanceRecord record = new AttendanceRecord(id, name, roll, dept, div, date, status);
        attendanceRef.child(id).setValue(record);
    }

    public interface StudentsCallback {
        void onResult(ArrayList<Student> studentList);
    }
}