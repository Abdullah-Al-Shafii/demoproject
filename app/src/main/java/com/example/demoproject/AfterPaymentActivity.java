package com.example.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AfterPaymentActivity extends AppCompatActivity {
    Button afterPaymentShowProfileButton,afterPaymentDownloadAdmitCardButton;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_payment);
        this.setTitle("Congratulation");
        afterPaymentDownloadAdmitCardButton=(Button)findViewById(R.id.AfterPaymentDownloadAdmitCardId);
        afterPaymentDownloadAdmitCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=getIntent().getExtras();
                if(bundle!=null)
                {
                    email= bundle.getString("email");
                }
                Intent intent=new Intent(getApplicationContext(),DownloadAdmitCard.class);
                intent.putExtra("email",""+email);
                startActivity(intent);
            }
        });

    }
}