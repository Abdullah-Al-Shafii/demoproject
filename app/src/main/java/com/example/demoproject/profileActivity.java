package com.example.demoproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reginald.editspinner.EditSpinner;

import org.jetbrains.annotations.NotNull;

public class profileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    EditText hscRollnumberEditText,hscRegistrationNumberEditText,hscGPAEditText;
    FloatingActionButton nextButton;
    DatabaseReference databaseReference;
    String email;
    DatabaseError databaseError;
    EditSpinner hscboardspinner;
    EditSpinner hscpassingyearspinner;
    String[] hscboards,hscpassingyear;
    private  boolean isfirstselected=true;
    private boolean firstselected=true;
    String hscboard,hscpassyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setTitle("HSC information");
        drawerLayout=(DrawerLayout)findViewById(R.id.HSCInformationDrawLayoutId);
        actionBarDrawerToggle=new ActionBarDrawerToggle(profileActivity.this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationViewId);
        navigationView.setNavigationItemSelectedListener(this);
        hscRollnumberEditText=(EditText)findViewById(R.id.HSCRollNumberEditTextId);
        hscRegistrationNumberEditText=(EditText)findViewById(R.id.HSCRegistrationNumberEditTextId);
        hscGPAEditText=(EditText)findViewById(R.id.HSCGPAEditTextId);
        nextButton=(FloatingActionButton) findViewById(R.id.nextbuttonid);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");
        hscboardspinner=(EditSpinner) findViewById(R.id.hscboardspinnerid);
        hscpassingyearspinner=(EditSpinner) findViewById(R.id.hscpassingyearspinnerid);
        hscboards=getResources().getStringArray(R.array.Boards);
        hscpassingyear=getResources().getStringArray(R.array.HSCpassingyears);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.spinnersamplelayout,R.id.spinnertextviewid,hscboards);
        hscboardspinner.setAdapter(adapter);

        hscboardspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    if(isfirstselected==true)
                    {
                        isfirstselected=false;
                    }
                    else{
                    hscboard="";
                    Toast.makeText(getApplicationContext(),"Select a board properly",Toast.LENGTH_LONG).show();}

                }
                else{
                    hscboard=hscboards[position];
                    isfirstselected=false;
                }
            }
        });

        ArrayAdapter<String> adapter1=new ArrayAdapter<>(this,R.layout.spinnersamplelayout,R.id.spinnertextviewid,hscpassingyear);
        hscpassingyearspinner.setAdapter(adapter1);

        hscpassingyearspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    if(firstselected==true)
                    {
                        firstselected=false;
                    }
                    else{
                        hscpassyear="";
                        Toast.makeText(getApplicationContext(),"Select a passing year properly",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    hscpassyear = hscpassingyear[position];
                    firstselected=false;
                }
            }
        });

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
                      hscRollnumberEditText.setText(""+dataSnapshot.child("hscRollNumber").getValue(String.class));
                      hscRegistrationNumberEditText.setText(""+dataSnapshot.child("hscRegistrationNumber").getValue(String.class));
                      hscboardspinner.setText(""+dataSnapshot.child("hscBoardName").getValue(String.class));
                      hscpassingyearspinner.setText(""+dataSnapshot.child("hscPassingYear").getValue(String.class));
                      hscGPAEditText.setText(""+dataSnapshot.child("hscGPA").getValue(String.class));
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveAndGo();
            }
        });

    }


    private void saveAndGo() {
        String hscRoll=hscRollnumberEditText.getText().toString().trim();
        String hscRegistration=hscRegistrationNumberEditText.getText().toString().trim();
        String hscGPA=hscGPAEditText.getText().toString().trim();
        hscboard=hscboardspinner.getText().toString().trim();
        hscpassyear=hscpassingyearspinner.getText().toString().trim();
        double hscgpa=Double.parseDouble(hscGPA);
        if(hscgpa<4.00)
        {
            AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(profileActivity.this);
            alertDialogBuilder.setTitle("Alert dialog");
            alertDialogBuilder.setMessage("Sorry!you are not eligible.Do you want to see requirement?");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle bundle=getIntent().getExtras();
                    if(bundle!=null)
                    {
                        email= bundle.getString("email");
                    }
                    Intent intent=new Intent(getApplicationContext(),FirstPageRequirement.class);
                    intent.putExtra("email",""+email);
                    startActivity(intent);
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(profileActivity.this,"check your gpa again",Toast.LENGTH_SHORT).show();
                }
            });

            alertDialogBuilder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(profileActivity.this,"check your gpa again",Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            if (!TextUtils.isEmpty(hscRoll) && !TextUtils.isEmpty(hscRegistration) && !TextUtils.isEmpty(hscboard) && !TextUtils.isEmpty(hscpassyear) && !TextUtils.isEmpty(hscGPA)) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.child("email").getValue().equals(email)) {
                                dataSnapshot.getRef().child("hscRollNumber").setValue(hscRoll);
                                dataSnapshot.getRef().child("hscRegistrationNumber").setValue(hscRegistration);
                                dataSnapshot.getRef().child("hscBoardName").setValue(hscboard);
                                dataSnapshot.getRef().child("hscPassingYear").setValue(hscpassyear);
                                dataSnapshot.getRef().child("hscGPA").setValue(hscGPA);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(), TotalNumberActivity.class);
                intent.putExtra("email", "" + email);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Enter data properly", Toast.LENGTH_SHORT).show();
            }
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
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
       else if(item.getItemId()==R.id.Usersignoutid)
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


    @Override
    public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
        if(item.getItemId()==R.id.ApplyNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),profileActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.ProfileNavigationId)
        {
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null)
            {
                email= bundle.getString("email");
            }
            Intent intent=new Intent(getApplicationContext(),profileActivity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.PayFeesNavigationId)
        {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            String notpaid = "not paid yet";
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("email").getValue().equals(email)) {
                            if (dataSnapshot.child("paymentStatus").getValue().equals(notpaid)) {
                                Intent intent = new Intent(getApplicationContext(), PaymentMethodActivity.class);
                                intent.putExtra("email", "" + email);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"You have already paid your fees", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }


            });

        }
        else if(item.getItemId()==R.id.DownloadAdmitCardNavigationId)
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
        else if(item.getItemId()==R.id.ResultNavigationId)
        {
            String publish = "publish";
            String notpublish = "not publish";
            DatabaseReference mdatabaseReference;
            DatabaseError mdatabaseError;
            mdatabaseReference = FirebaseDatabase.getInstance().getReference("Admin");
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                email = bundle.getString("email");
            }
            mdatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.child("isResultPublish").getValue().equals(publish)) {
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            intent.putExtra("email", "" + email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Result is not yet published", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if(item.getItemId()==R.id.LogOutNavigationId)
        {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
            startActivity(intent);
        }
        return false;
    }
}

