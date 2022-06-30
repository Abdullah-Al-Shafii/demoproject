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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class OtherInformationActivity extends AppCompatActivity {
    private RadioGroup genderRadioGroup,groupRadioGroup,quotaRadioGroup;
    private RadioButton genderRadioButton,groupRadioButton,quotaRadioButton;
    private Button otherinformationButton;
    String selectedGender,selectedGroup,selectedQuota;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    int genderselected,groupselected,quotaselected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_information);
        this.setTitle("other Information");
        genderRadioGroup=(RadioGroup)findViewById(R.id.genderRadioGroupId);
        groupRadioGroup=(RadioGroup)findViewById(R.id.groupRadioGroupId);
        quotaRadioGroup=(RadioGroup)findViewById(R.id.quotaRadioGroupId);
        otherinformationButton=(Button)findViewById(R.id.otherinformationbuttonid);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        otherinformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndGo();
            }
        });

    }
    private void saveAndGo() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            email= bundle.getString("email");
        }
        genderselected= genderRadioGroup.getCheckedRadioButtonId();
        genderRadioButton=(RadioButton)findViewById(genderselected);
        selectedGender=genderRadioButton.getText().toString();
        groupselected= groupRadioGroup.getCheckedRadioButtonId();
        groupRadioButton=(RadioButton)findViewById(groupselected);
        selectedGroup=groupRadioButton.getText().toString();
        quotaselected= quotaRadioGroup.getCheckedRadioButtonId();
        quotaRadioButton=(RadioButton)findViewById(quotaselected);
        selectedQuota=quotaRadioButton.getText().toString();
        if(!TextUtils.isEmpty(selectedGender) && !TextUtils.isEmpty(selectedGroup) && !TextUtils.isEmpty(selectedQuota))
        {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            dataSnapshot.getRef().child("gender").setValue(selectedGender);
                            dataSnapshot.getRef().child("group").setValue(selectedGroup);
                            dataSnapshot.getRef().child("quota").setValue(selectedQuota);
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