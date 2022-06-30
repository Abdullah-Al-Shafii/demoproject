package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ContactSActivity extends AppCompatActivity {
    EditText permanentaddressEditText,presentaddressEditText,phoneEditText;
    TextView profileemailEditText;
    FloatingActionButton contactinformationbutton;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_sactivity);
        this.setTitle("Contact information");
        permanentaddressEditText=(EditText)findViewById(R.id.PermanentAddressEditTextId);
        presentaddressEditText=(EditText)findViewById(R.id.PresentAddressEditTextId);
        phoneEditText=(EditText)findViewById(R.id.PhoneNumberEditTextId);
        profileemailEditText=(TextView) findViewById(R.id.ProfileEmailEditTextId);
        contactinformationbutton=(FloatingActionButton)findViewById(R.id.contactinformationbuttonid);
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
                        permanentaddressEditText.setText(""+dataSnapshot.child("permanentAdress").getValue(String.class));
                        presentaddressEditText.setText(""+dataSnapshot.child("presentAddress").getValue(String.class));
                        phoneEditText.setText(""+dataSnapshot.child("mobileNumber").getValue(String.class));
                        profileemailEditText.setText(""+dataSnapshot.child("email").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        contactinformationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });


    }

    private void saveAndGo() {
        String permanentAdress=permanentaddressEditText.getText().toString().trim();
        String presentAddress=presentaddressEditText.getText().toString().trim();
        String mobileNumber=phoneEditText.getText().toString().trim();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        if(!TextUtils.isEmpty(permanentAdress) && !TextUtils.isEmpty(presentAddress) && !TextUtils.isEmpty(mobileNumber))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            dataSnapshot.getRef().child("permanentAdress").setValue(permanentAdress);
                            dataSnapshot.getRef().child("presentAddress").setValue(presentAddress);
                            dataSnapshot.getRef().child("mobileNumber").setValue(mobileNumber);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
             Intent intent=new Intent(getApplicationContext(),ImageSignatureActivity.class);
             intent.putExtra("email",""+email);
             startActivity(intent);

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