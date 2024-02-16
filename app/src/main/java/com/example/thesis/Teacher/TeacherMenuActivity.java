package com.example.thesis.Teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.thesis.Authentication.LoginActivity;
import com.example.thesis.Messaging.FCMSender;
import com.example.thesis.Messaging.NotificationMessage;
import com.example.thesis.R;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class TeacherMenuActivity extends AppCompatActivity {

    Button logoutButton, showAttendanceListButton, requestFaceCheckingAttendanceButton, requestVoiceCheckingAttendanceButton;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_menu);


        reference = FirebaseDatabase.getInstance().getReference();

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        showAttendanceListButton = findViewById(R.id.showAttendanceListButton);
        showAttendanceListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AttendaceList.class));
            }
        });

        requestFaceCheckingAttendanceButton = findViewById(R.id.requestFaceCheckingAttendanceButton);
        requestFaceCheckingAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestFaceCheckingAttendance();
            }
        });

        requestVoiceCheckingAttendanceButton = findViewById(R.id.requestVoiceCheckingAttendanceButton);
        requestVoiceCheckingAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestVoiceCheckingAttendance();
            }
        });



    }

    private void requestVoiceCheckingAttendance() {
        sendNotification("Voice Checking Attendance Requested");
    }

    public void requestFaceCheckingAttendance() {
        sendNotification("Face Checking Attendance Requested");
    }

    public void sendNotification(String message){
        new FCMSender().send(String.format(NotificationMessage.message,"messaging",message), new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                TeacherMenuActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.code()==200){
                            Toast.makeText(TeacherMenuActivity.this, "Notification sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}