package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class UserFirstPage extends AppCompatActivity implements View.OnClickListener {
    CardView applyCardView,admissionCardView,universityCardView,eligiblelistCardView,meritlistCardView,admitCardCardView;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_first_page);
        applyCardView=findViewById(R.id.userApplyCardViewid);
        admissionCardView=findViewById(R.id.userAdmissionCardViewid);
        universityCardView=findViewById(R.id.userUniversityCardViewid);
        eligiblelistCardView=findViewById(R.id.userEligibleListCardViewid);
        meritlistCardView=findViewById(R.id.userMeritListCardViewid);
        admitCardCardView=findViewById(R.id.userAdmitCardCardViewid);
        applyCardView.setOnClickListener(this);
        admissionCardView.setOnClickListener(this);
        universityCardView.setOnClickListener(this);
        eligiblelistCardView.setOnClickListener(this);
        meritlistCardView.setOnClickListener(this);
        admitCardCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.userApplyCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),FirstPageRequirement.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(v.getId()==R.id.userAdmissionCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(v.getId()==R.id.userUniversityCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(v.getId()==R.id.userEligibleListCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),CreateEligibleList.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(v.getId()==R.id.userMeritListCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),CreatingMeritList.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(v.getId()==R.id.userAdmitCardCardViewid)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),DownloadAdmitCard.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
    }
}