package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class BasicInformationActivity extends AppCompatActivity {
    EditText nameEditText,fathernameEditText,mothernameEditText,guardiannameEditText,relationwithguardianEditText,guardiancontactnumberEditText;
    FloatingActionButton basicinformationbutton;
    DatabaseReference databaseReference;
    EditText dateofbirthEditText;
    String email;
    DatePickerDialog datePickerDialog;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);
        this.setTitle("Basic information");
        nameEditText=(EditText)findViewById(R.id.NameEditTextId);
        fathernameEditText=(EditText)findViewById(R.id.FatherNameEditTextId);
        mothernameEditText=(EditText)findViewById(R.id.MotherNameEditTextId);
        guardiannameEditText=(EditText)findViewById(R.id.GuardianNameEditTextId);
        relationwithguardianEditText=(EditText)findViewById(R.id.RelationwithguardianEditTextId);
        guardiancontactnumberEditText=(EditText)findViewById(R.id.GuardiancontactnumberEditTextId);
        dateofbirthEditText=(EditText)findViewById(R.id.DateOfBirthEditTextId);
        basicinformationbutton=(FloatingActionButton)findViewById(R.id.basicinformationbuttonid);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("email").getValue().equals(email)) {
                        nameEditText.setText(""+dataSnapshot.child("name").getValue(String.class));
                        fathernameEditText.setText(""+dataSnapshot.child("fatherName").getValue(String.class));
                        dateofbirthEditText.setText(""+dataSnapshot.child("dateofBirth").getValue(String.class));
                        mothernameEditText.setText(""+dataSnapshot.child("motherName").getValue(String.class));
                        guardiannameEditText.setText(""+dataSnapshot.child("guardianName").getValue(String.class));
                        relationwithguardianEditText.setText(""+dataSnapshot.child("relationwithgurdian").getValue(String.class));
                        guardiancontactnumberEditText.setText(""+dataSnapshot.child("gurdiancontactnumber").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        dateofbirthEditText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePicker datePicker=new DatePicker(BasicInformationActivity.this);
                int currentDate=datePicker.getDayOfMonth();
                int currentMonth=datePicker.getMonth();
                int currentYear=datePicker.getYear();
                datePickerDialog=new DatePickerDialog(BasicInformationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateofbirthEditText.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },currentYear,currentMonth,currentDate);
                datePickerDialog.show();
            }
        });

        basicinformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });


    }

    private void saveAndGo() {
        String name=nameEditText.getText().toString().trim();
        String fatherName=fathernameEditText.getText().toString().trim();
        String motherName=mothernameEditText.getText().toString().trim();
        String guardianName=guardiannameEditText.getText().toString().trim();
        String Relationwithguardian=relationwithguardianEditText.getText().toString().trim();
        String gurdiancontactnumber=guardiancontactnumberEditText.getText().toString().trim();
        String dateofBirth=dateofbirthEditText.getText().toString().trim();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(fatherName) && !TextUtils.isEmpty(motherName) && !TextUtils.isEmpty(guardianName) && !TextUtils.isEmpty(Relationwithguardian) && !TextUtils.isEmpty(gurdiancontactnumber) )
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            dataSnapshot.getRef().child("name").setValue(name);
                            dataSnapshot.getRef().child("fatherName").setValue(fatherName);
                            dataSnapshot.getRef().child("motherName").setValue(motherName);
                            dataSnapshot.getRef().child("dateofBirth").setValue(dateofBirth);
                            dataSnapshot.getRef().child("guardianName").setValue(guardianName);
                            dataSnapshot.getRef().child("motherName").setValue(motherName);
                            dataSnapshot.getRef().child("relationwithgurdian").setValue(Relationwithguardian);
                            dataSnapshot.getRef().child("gurdiancontactnumber").setValue(gurdiancontactnumber);
                        }

                    }
                    Intent intent=new Intent(getApplicationContext(),ContactSActivity.class);
                    intent.putExtra("email",""+email);
                    startActivity(intent);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.usersignoutmenulayout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Usersignoutid)
        {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}