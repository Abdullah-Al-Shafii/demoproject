package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class UpdateExamCentre extends AppCompatActivity {
    EditText adminExamCentreApplicantIdEditText,adminExamCentreEditText;
    Button adminExamCentreButton;
    DatabaseReference databaseReference;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exam_centre);
        this.setTitle("Update Exam Centre");
        adminExamCentreApplicantIdEditText=(EditText)findViewById(R.id.AdminExamCentreApplicantIdEditTextId);
        adminExamCentreEditText=(EditText)findViewById(R.id.AdminExamCentreEditTextId);
        adminExamCentreButton=(Button) findViewById(R.id.AdminExamCentreButtonId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        adminExamCentreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=adminExamCentreApplicantIdEditText.getText().toString().trim();
                String examCentre=adminExamCentreEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(examCentre) )
                {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("id").getValue().equals(id)) {
                                    dataSnapshot.getRef().child("examCentre").setValue(examCentre);
                                    Toast.makeText(getApplicationContext(),"Exam Centre Updated Successfully",Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else
                {
                    Toast.makeText(getApplicationContext(),"Enter data properly",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}