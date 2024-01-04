package com.example.thesis.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.thesis.FaceRecognition.FaceClassifier;
import com.example.thesis.LoginActivity;
import com.example.thesis.MainActivity;
import com.example.thesis.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class StudentMenuActivity extends AppCompatActivity {

    public static HashMap<String, FaceClassifier.Recognition> registered = new HashMap<>();

    Button faceRegisterButton,faceRecognizeButton, logoutButton;

    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseUser user = mAuth.getCurrentUser();
    public static String userID = user.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);
        //get Data from file
        File file = new File(getExternalFilesDir(null), "RegisteredFacesData.ser");
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            HashMap<String, FaceClassifier.Recognition> readMap = (HashMap<String, FaceClassifier.Recognition>) ois.readObject();
            StudentMenuActivity.registered = readMap;
        } catch (IOException | ClassNotFoundException e) {
            Log.d("tryRead", e.toString());
        }

        faceRegisterButton = findViewById(R.id.faceRegisterButton);
        faceRecognizeButton = findViewById(R.id.faceRecognizeButton);
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