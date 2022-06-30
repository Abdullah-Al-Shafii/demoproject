package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class BkashPaymentActivity extends AppCompatActivity {
    EditText bkashNumberEditText,bkashTransactionEditText;
    Button bkashbutton;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash_payment);
        this.setTitle("pay admission fees via rocket");
        bkashNumberEditText=(EditText)findViewById(R.id.BkashNumberEditTextId);
        bkashTransactionEditText=(EditText)findViewById(R.id.BkashTransactionIDEditTextId);
        bkashbutton=(Button)findViewById(R.id.bkashButtonId);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        bkashbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });
    }

    private void saveAndGo() {
        String bkashNumber=bkashNumberEditText.getText().toString().trim();
        String bkashTransactionID=bkashTransactionEditText.getText().toString().trim();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        if(!TextUtils.isEmpty(bkashNumber) && !TextUtils.isEmpty(bkashTransactionID))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            dataSnapshot.getRef().child("paymentStatus").setValue("paid");
                            dataSnapshot.getRef().child("paymentNumber").setValue(bkashNumber);
                            dataSnapshot.getRef().child("transactionID").setValue(bkashTransactionID);
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
          /*  Intent intent=new Intent(getApplicationContext(),OtherInformationActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);*/

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