
package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ShowProfileActivity extends AppCompatActivity {
    TextView hscRollnumberTextView,hscRegistrationNumberTextView;
    TextView sscRollnumberTextView,sscRegistrationNumberTextView;
    TextView dateofBirthTextView,emailTextView;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        hscRollnumberTextView=(TextView)findViewById(R.id.HSCRollNumberTextViewId);
        hscRegistrationNumberTextView=(TextView)findViewById(R.id.HSCRegistrationNumberTextViewId);
        sscRollnumberTextView=(TextView)findViewById(R.id.SSCRollNumberTextViewId);
        sscRegistrationNumberTextView=(TextView)findViewById(R.id.SSCRegistrationNumberTextViewId);
        dateofBirthTextView=(TextView) findViewById(R.id.DateofBirthTextViewId);
        emailTextView=(TextView)findViewById(R.id.EmailTextViewId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");

    }

    protected void onStart() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().equals(email)) {
                       hscRollnumberTextView.setText("HSC Roll Number: "+dataSnapshot.child("hscRollNumber").getValue(String.class));
                       hscRegistrationNumberTextView.setText("HSC Registration Number: "+dataSnapshot.child("hscRegistrationNumber").getValue(String.class));
                       sscRollnumberTextView.setText("SSC Roll Number: "+dataSnapshot.child("sscRollNumber").getValue(String.class));
                       sscRegistrationNumberTextView.setText("HSC Registration Number: "+dataSnapshot.child("sscRegistrationNumber").getValue(String.class));
                       dateofBirthTextView.setText("Date of Birth :"+dataSnapshot.child("dateofBirth").getValue(String.class));
                       emailTextView.setText("Email : "+dataSnapshot.child("email").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        super.onStart();
    }
}