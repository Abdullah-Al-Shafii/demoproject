package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExamSystemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_system);
        this.setTitle("Admission Exam System");
    }
}