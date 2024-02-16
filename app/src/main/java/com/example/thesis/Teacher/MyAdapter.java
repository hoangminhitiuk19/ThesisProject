package com.example.thesis.Teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.R;
import com.example.thesis.Student.Student;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<Student> list;

    public MyAdapter(Context context, ArrayList<Student> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_attendace_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.name.setText(student.getStudentName());
        holder.id.setText(student.getStudentId());
        holder.faceStatus.setText(student.getFaceCheckingAttendanceTime());
        holder.voiceStatus.setText(student.getVoiceCheckingAttendanceTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, id, faceStatus, voiceStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            id = itemView.findViewById(R.id.student_id);
            faceStatus = itemView.findViewById(R.id.student_face_check_in);
            voiceStatus = itemView.findViewById(R.id.student_voice_check_in);
        }
    }
}
