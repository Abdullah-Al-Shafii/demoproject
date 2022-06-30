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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class RocketPaymentActivity extends AppCompatActivity {
    EditText rocketNumberEditText,rocketTransactionEditText;
    Button rocketbutton;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_payment);
        this.setTitle("pay admission fees via rocket");
        rocketNumberEditText=(EditText)findViewById(R.id.RocketNumberEditTextId);
        rocketTransactionEditText=(EditText)findViewById(R.id.RocketTransactionIDEditTextId);
        rocketbutton=(Button)findViewById(R.id.rocketButtonId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        rocketbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });

    }
    private void saveAndGo() {
        String rocketNumber=rocketNumberEditText.getText().toString().trim();
        String rocketTransactionID=rocketTransactionEditText.getText().toString().trim();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        if(!TextUtils.isEmpty(rocketNumber) && !TextUtils.isEmpty(rocketTransactionID))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            dataSnapshot.getRef().child("paymentStatus").setValue("paid");
                            dataSnapshot.getRef().child("paymentNumber").setValue(rocketNumber);
                            dataSnapshot.getRef().child("transactionID").setValue(rocketTransactionID);
                            String id=""+dataSnapshot.child("id").getValue(String.class);
                            dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-108");
                       /*  if(id.charAt(1)=='A')
                            {
                                dataSnapshot.getRef().child("examCentre").setValue("URP Building,Room-101");
                            }
                            else if(id.charAt(1)=='M')
                            {
                                dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-101");
                                if(id.charAt(2)=='a') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-102");
                                }
                                else if(id.charAt(2)=='b') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-103");
                                }
                                else if(id.charAt(2)=='c') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-104");
                                }
                                else if(id.charAt(2)=='d') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-105");
                                }
                                else if(id.charAt(2)=='e') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-106");
                                }
                                else if(id.charAt(2)=='f') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-107");
                                }
                                else if(id.charAt(2)=='g') {
                                    dataSnapshot.getRef().child("examCentre").setValue("EEE Building,Room-108");
                                }
                            }
                            else if(id.charAt(1)=='N')
                            {
                                dataSnapshot.getRef().child("examCentre").setValue("New Academic Building,Block-D,Room-102");
                            }
                            else
                            {
                                dataSnapshot.getRef().child("examCentre").setValue("New Academic Building,Block-C,Room-106");
                            }*/
                        }

                    }
                    finish();
                    Intent intent=new Intent(getApplicationContext(),Congratulation.class);
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