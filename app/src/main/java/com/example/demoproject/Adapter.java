package com.example.demoproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter  extends ArrayAdapter <Students> {
    private Activity context;
    private List<Students> studentsList;


    public Adapter(Activity context,  List<Students> studentsList) {
        super(context,R.layout.allviewsamplemodifiedlayout,studentsList);
        this.context = context;
        this.studentsList = studentsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view= layoutInflater.inflate(R.layout.allviewsamplemodifiedlayout,null,true);
        Students student=studentsList.get(position);
        TextView textView1=view.findViewById(R.id.studentsdataviewApplicationIDTextViewid);
        TextView textView2=view.findViewById(R.id.studentsdataviewnameTextViewid);
        TextView textView3=view.findViewById(R.id.studentsdataviewhscregistrationTextViewid);
        TextView textView4=view.findViewById(R.id.studentsdataviewexamcentreTextViewid);
        TextView textView5=view.findViewById(R.id.studentsdataviewpaymentstatusTextViewid);
        TextView textView6=view.findViewById(R.id.studentsdataviewmeritpositionTextViewid);
        textView1.setText("ID: "+student.id);
        textView2.setText("Name : "+student.name);
        textView3.setText("Hsc Registration Number : "+student.hscRegistrationNumber);
        textView4.setText("Exam centre : "+student.examCentre);
        textView5.setText("HSC G.P.A. : "+student.hscGPA);
        textView6.setText("HSC number in physics,chemistry,mathematics & english: "+student.hscTotalMarks);
        return view;
    }
}
