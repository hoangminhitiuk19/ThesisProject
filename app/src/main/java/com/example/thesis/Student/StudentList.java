package com.example.thesis.Student;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.thesis.R;
import com.example.thesis.Student.Student;

import java.util.List;

public class StudentList extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> studentList;

    public StudentList(Activity context, List<Student> studentList) {
        super(context, R.layout.activity_attendace_list, studentList);
        this.context = context;
        this.studentList = studentList;
    }

    /*@NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View studentListView = inflater.inflate(R.layout.activity_attendace_list, null, true);

        TextView studentNameTextView = studentListView.findViewById(R.id.student_name);
        TextView studentIdTextView = studentListView.findViewById(R.id.studentAttendanceStatus);

        Student student = studentList.get(position);
        studentNameTextView.setText(student.getStudentName());
        studentIdTextView.setText(student.getFaceCheckingAttendanceStatus());

        return studentListView;
    }*/
}
