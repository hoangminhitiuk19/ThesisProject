package com.example.thesis.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.thesis.FaceRecognition.FaceClassifier;
import com.example.thesis.Authentication.LoginActivity;
import com.example.thesis.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class StudentMenuActivity extends AppCompatActivity {

    public static HashMap<String, FaceClassifier.Recognition> faceRegistered = new HashMap<>();

    public static HashMap<String, String> voiceRegistered = new HashMap<>();

    Button faceRegisterButton,faceRecognizeButton, voiceRegisterButton, voiceRecognizeButton, logoutButton;
    DatabaseReference reference;
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseUser user = mAuth.getCurrentUser();
    public static String userID = user.getUid();
    public static String studentID = user.getEmail().substring(0, 11);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);


        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        //TODO GET REGISTERED FACE DATA FROM FILE
        File faceRegisteredFile = new File(getExternalFilesDir(null), "RegisteredFacesData.ser");
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(faceRegisteredFile);
            ois = new ObjectInputStream(fis);
            HashMap<String, FaceClassifier.Recognition> readRegisteredFaceMap = (HashMap<String, FaceClassifier.Recognition>) ois.readObject();
            StudentMenuActivity.faceRegistered = readRegisteredFaceMap;
        } catch (IOException | ClassNotFoundException e) {
            Log.d("tryRead", e.toString());
        }
        //TODO GET REGISTERED VOICE DATA FROM FILE
        File voiceRegisteredFile = new File(getExternalFilesDir(null), "RegisteredVoicesData.ser");
        try {
            fis = new FileInputStream(voiceRegisteredFile);
            ois = new ObjectInputStream(fis);
            HashMap<String, String> readRegisteredVoiceMap = (HashMap<String, String>) ois.readObject();
            StudentMenuActivity.voiceRegistered = readRegisteredVoiceMap;
        } catch (IOException | ClassNotFoundException e) {
            Log.d("tryRead", e.toString());
        }

        faceRegisterButton = findViewById(R.id.faceRegisterButton);
        faceRecognizeButton = findViewById(R.id.faceRecognizeButton);
        voiceRegisterButton = findViewById(R.id.voiceRegisterButton);
        voiceRecognizeButton = findViewById(R.id.voiceRecognizeButton);
        logoutButton = findViewById(R.id.logoutButton);

        faceRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMenuActivity.this, FaceRegisterActivity.class));
            }
        });

        faceRecognizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMenuActivity.this, FaceRecognitionActivity.class));
            }
        });

        voiceRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMenuActivity.this, VoiceRegisterActivity.class));
            }
        });

        voiceRecognizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentMenuActivity.this, VoiceRecognitionActivity.class));
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

}