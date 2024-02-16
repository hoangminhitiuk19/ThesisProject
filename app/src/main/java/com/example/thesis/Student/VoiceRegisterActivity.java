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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thesis.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class VoiceRegisterActivity extends AppCompatActivity {

    TextInputLayout questionInputLayout;
    MaterialAutoCompleteTextView questionAutoCompleteTextView;
    EditText answerEditText;
    Button voiceRegisterButton;
    Button voiceInputButton;
    SpeechRecognizer speechRecognizer;
    boolean isListening = false;
    public static final int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_register);


        questionInputLayout = findViewById(R.id.questionInputLayout);
        questionAutoCompleteTextView = findViewById(R.id.questionAutoCompleteTextView);
        answerEditText = findViewById(R.id.answerEditText);
        voiceRegisterButton = findViewById(R.id.voiceRegisterButton);
        voiceInputButton = findViewById(R.id.voiceInputButton);

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

        voiceRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = questionAutoCompleteTextView.getText().toString();
                String answer = answerEditText.getText().toString();
                if (question.isEmpty()){
                    questionInputLayout.setError("Question is required");
                    questionInputLayout.requestFocus();
                    return;
                } else if (answer.isEmpty()){
                    answerEditText.setError("Answer is required");
                    answerEditText.requestFocus();
                    return;
                } else {
                    // TODO save the face embedding and name to the Firebase database
                    StudentMenuActivity.voiceRegistered.put(question, answer);
                    Toast.makeText(VoiceRegisterActivity.this, "YOUR ANSWER IS SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();
                    try{
                        File file = new File(getExternalFilesDir(null), "RegisteredVoicesData.ser");
                        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(StudentMenuActivity.voiceRegistered);
                        Log.d("tryWrite", "Written Successfully");
                        oos.close();
                        fos.close();
                    } catch (IOException e) {
                        Log.d("tryWrite", e.toString());
                    }
                }
            }
        });
    }
}