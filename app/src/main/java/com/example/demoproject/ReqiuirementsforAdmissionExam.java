package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ReqiuirementsforAdmissionExam extends AppCompatActivity {
    TextView requirementsforAdmissiontest;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqiuirementsfor_admission_exam);
        requirementsforAdmissiontest=(TextView)findViewById(R.id.RequirementsforAdmissionTest);
        typeface=Typeface.createFromAsset(getAssets(),"SutonnyMJBold.ttf");
        requirementsforAdmissiontest.setTypeface(typeface);
    }
}