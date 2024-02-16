package com.example.thesis.Student;

public class Student {
    String studentName;
    String studentId;
    String faceCheckingAttendanceTime;

    String voiceCheckingAttendanceTime;

    public Student() {
    }

    public Student(String studentName, String studentId, String faceCheckingAttendanceTime) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.faceCheckingAttendanceTime = faceCheckingAttendanceTime;
        this.voiceCheckingAttendanceTime = voiceCheckingAttendanceTime;
    }

    public Student(String voiceCheckingAttendanceTime) {
        this.voiceCheckingAttendanceTime = voiceCheckingAttendanceTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }
    public String getFaceCheckingAttendanceTime() {
        return faceCheckingAttendanceTime;
    }

    public String getVoiceCheckingAttendanceTime() {
        return voiceCheckingAttendanceTime;
    }
}
