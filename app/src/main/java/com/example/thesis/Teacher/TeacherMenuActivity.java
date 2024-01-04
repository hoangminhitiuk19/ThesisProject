package com.example.thesis.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thesis.LoginActivity;
import com.example.thesis.R;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        Button showAttendanceListButton = findViewById(R.id.showAttendanceListButton);
        showAttendanceListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AttendaceList.class));
            }
        });

        Button requestCheckingAttendanceButton = findViewById(R.id.requestCheckingAttendanceButton);
requestCheckingAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCheckingAttendance();
            }
        });

    }

    void requestCheckingAttendance() {
    }
}