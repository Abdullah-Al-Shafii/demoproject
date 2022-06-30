package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Departments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        this.setTitle("Departments with no of students");
    }
}