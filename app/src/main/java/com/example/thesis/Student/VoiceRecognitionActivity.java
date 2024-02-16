package com.example.thesis.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thesis.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VoiceRecognitionActivity extends AppCompatActivity {
    TextInputLayout questionInputLayout;
    MaterialAutoCompleteTextView questionAutoCompleteTextView;
    EditText answerEditText;
    Button voiceRecognizerButton;
    Button voiceInputButton;
    SpeechRecognizer speechRecognizer;
    boolean isListening = false;
    public static final int PERMISSION_CODE = 100;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognition);

        questionInputLayout = findViewById(R.id.questionInputLayout);
        questionAutoCompleteTextView = findViewById(R.id.questionAutoCompleteTextView);
        answerEditText = findViewById(R.id.answerEditText);
        voiceRecognizerButton = findViewById(R.id.voiceRegisterButton);
        voiceInputButton = findViewById(R.id.voiceInputButton);

        reference = FirebaseDatabase.getInstance().getReference();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
                String[] permission = {Manifest.permission.RECORD_AUDIO};
                requestPermissions(permission, PERMISSION_CODE);
            }
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);


        voiceInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isListening){
                    voiceInputButton.setText("Stop Recording");
                    speechRecognizer.startListening(speechRecognizerIntent);
                    isListening = true;
                }else{
                    voiceInputButton.setText("Start Recording");
                    speechRecognizer.stopListening();
                    isListening = false;
                }
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (voiceResults != null){
                    answerEditText.setText(voiceResults.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        voiceRecognizerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionAutoCompleteTextView.getText().toString();
                String answer = answerEditText.getText().toString();
                if (question.isEmpty()){
                    questionInputLayout.setError("Question is required");
                    questionInputLayout.requestFocus();
                } else if (answer.isEmpty()){
                    answerEditText.setError("Answer is required");
                    answerEditText.requestFocus();
                } else {
                    if (StudentMenuActivity.voiceRegistered.containsKey(question) && StudentMenuActivity.voiceRegistered.get(question).equals(answer)){
                        Date currentTime = Calendar.getInstance().getTime();
                        //TODO Create a Student
                        Student studentVoiceCheckingAttendance = new Student(currentTime.toString());
                        //TODO add student's attendance to Firebase Database
                        reference.child("student").child(StudentMenuActivity.userID).child("voiceCheckingAttendance").setValue(studentVoiceCheckingAttendance);
                        Toast.makeText(VoiceRecognitionActivity.this, "YOU HAVE CHECKED ATTENDANCE", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(VoiceRecognitionActivity.this, "YOU HAVE NOT REGISTERED THIS QUESTION YET", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}