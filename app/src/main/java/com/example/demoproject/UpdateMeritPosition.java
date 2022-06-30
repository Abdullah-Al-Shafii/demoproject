package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateMeritPosition extends AppCompatActivity {
    EditText adminMeritPositionApplicantIdEditText,adminMeritPositionEditText;
    Button adminMeritPositionButton;
    DatabaseReference databaseReference;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_merit_position);
        this.setTitle("Update Merit Position");
        adminMeritPositionApplicantIdEditText=(EditText)findViewById(R.id.AdminMeritPositionApplicantIdEditTextId);
        adminMeritPositionEditText=(EditText)findViewById(R.id.AdminMeritPositionEditTextId);
        adminMeritPositionButton=(Button) findViewById(R.id.AdminMeritPositionButtonId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        adminMeritPositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=adminMeritPositionApplicantIdEditText.getText().toString().trim();
                String meritPosition=adminMeritPositionEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(id) && !TextUtils.isEmpty(meritPosition) )
                {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("id").getValue().equals(id)) {
                                    dataSnapshot.getRef().child("meritPosition").setValue(meritPosition);
                                    Toast.makeText(getApplicationContext(),"Merit Position Updated Successfully",Toast.LENGTH_LONG).show();
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